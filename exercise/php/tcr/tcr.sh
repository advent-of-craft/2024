#!/usr/bin/env bash
#
# Copyright (c) 2022 Murex
# 
# Permission is hereby granted, free of charge, to any person obtaining a copy
# of this software and associated documentation files (the "Software"), to deal
# in the Software without restriction, including without limitation the rights
# to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
# copies of the Software, and to permit persons to whom the Software is
# furnished to do so, subject to the following conditions:
# 
# The above copyright notice and this permission notice shall be included in all
# copies or substantial portions of the Software.
# 
# THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
# IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
# FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
# AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
# LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
# OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
# SOFTWARE.

set -u

BASE_DIR="$(cd "$(dirname -- "$0")" && pwd)"

BASE_URL="https://github.com/murex/TCR"

# ------------------------------------------------------------------------------
# TCR-Go directory structure and information
# ------------------------------------------------------------------------------

TCR_GO_DIR="$(dirname "${BASE_DIR}")/tcr"
TCR_DOWNLOAD_DIR="${TCR_GO_DIR}/download"
TCR_BIN_DIR="${TCR_GO_DIR}/bin"
TCR_DOC_DIR="${TCR_GO_DIR}/doc"
TCR_VERSION_FILE="${TCR_GO_DIR}/version.txt"

# ------------------------------------------------------------------------------
# Trace messages
# ------------------------------------------------------------------------------

trace_info() {
  message="$1"
  >&2 echo "[TCR] ${message}"
}

trace_error() {
  message="$1"
  >&2 echo "[TCR] ERROR: ${message}"
}

# ------------------------------------------------------------------------------
# Download TCR Go from TCR GitHub repository
# ------------------------------------------------------------------------------

download_tcr_go() {
  version="$1"
  os_family="$2"
  os_arch="$3"
  exe_path="$4"

  archive_ext="tar.gz"

  # Cleanup download directory
  rm -rf "${TCR_DOWNLOAD_DIR}" && mkdir -p "${TCR_DOWNLOAD_DIR}"

  # ----------------------------------------------------------------------------
  # 1) TCR Go Binary File
  # ----------------------------------------------------------------------------

  bin_archive_file="tcr_${version}_${os_family}_${os_arch}.${archive_ext}"
  bin_archive_url="${BASE_URL}/releases/download/v${version}/${bin_archive_file}"
  download_and_expand_archive "${bin_archive_file}" "${bin_archive_url}" "${TCR_DOWNLOAD_DIR}"
  [ $? -ne 0 ] && return 1

  # Move extracted files to bin directory
  bin_ext=$(get_bin_ext "${os_family}")
  extracted_bin="${TCR_DOWNLOAD_DIR}/tcr${bin_ext}"
  mkdir -p "${TCR_BIN_DIR}"
  mv -f "${extracted_bin}" "${exe_path}"
  mv -f "${TCR_DOWNLOAD_DIR}/README.md" "${TCR_BIN_DIR}"
  mv -f "${TCR_DOWNLOAD_DIR}/LICENSE.md" "${TCR_BIN_DIR}"

  # ----------------------------------------------------------------------------
  # 2) TCR Go Documentation files
  # ----------------------------------------------------------------------------

  src_archive_file="v${version}.${archive_ext}"
  src_archive_url="${BASE_URL}/archive/refs/tags/${src_archive_file}"
  download_and_expand_archive "${src_archive_file}" "${src_archive_url}" "${TCR_DOWNLOAD_DIR}"
  [ $? -ne 0 ] && return 1

  # Move extracted files to doc directory
  rm -rf "${TCR_DOC_DIR}"
  src_extracted_path="${TCR_DOWNLOAD_DIR}/TCR-${version}"
  mv -f "${src_extracted_path}/doc" "${TCR_DOC_DIR}"

  # Clean up after download
  rm -rf "${TCR_DOWNLOAD_DIR}"
}

# ------------------------------------------------------------------------------
# Download an expand an archive from GitHub Release
# ------------------------------------------------------------------------------

download_and_expand_archive() {
  archive_file="$1"
  archive_url="$2"
  output_dir="$3"

  trace_info "Downloading ${archive_file}"
  
  curl --silent \
       --fail \
       --dump-header "${output_dir}/${archive_file}".response \
       --location "${archive_url}" \
       --output "${output_dir}/${archive_file}"
  if [ $? -ne 0 ]; then
    trace_error "Failed to download ${archive_url}"
    return 1
  fi

  tar -zxf "${output_dir}/${archive_file}" --directory "${output_dir}"
  if [ $? -ne 0 ]; then
    trace_error "Failed to extract ${archive_file}"
    return 1
  fi
}

# ------------------------------------------------------------------------------
# Return family of running operating system (Linux / Darwin / Windows)
# ------------------------------------------------------------------------------

get_os_family() {
  os_name=$(uname -s)
  case ${os_name} in
  Darwin | Linux)
    echo "${os_name}"
    return 0
    ;;
  MINGW64_NT-*)
    echo "Windows"
    return 0
    ;;
  *)
    trace_error "OS $(os_name) is currently not supported"
    return 1
    ;;
  esac
}

# ------------------------------------------------------------------------------
# Return binary file extension for OS family
# ------------------------------------------------------------------------------

get_bin_ext() {
  os_family="$1"
  if [ "${os_family}" = Windows ]; then
    echo ".exe"
  else
    echo ""
  fi
}

# ------------------------------------------------------------------------------
# Return expected TCR version
# ------------------------------------------------------------------------------

retrieve_expected_tcr_version() {
  if [ -f "${TCR_VERSION_FILE}" ]; then
    expected_version=$(awk '{ print $2 }' < "${TCR_VERSION_FILE}")
    echo "${expected_version}"
    return 0
  else
    trace_error "Version file not found: ${TCR_VERSION_FILE}"
    return 1
  fi
}

# ------------------------------------------------------------------------------
# Return current TCR version
# ------------------------------------------------------------------------------

retrieve_current_tcr_version() {
  exe_path="$1"
  current_version=$("${exe_path}" --version | awk '{ print $3 }')
  echo "${current_version}"
  return 0
}

# ------------------------------------------------------------------------------
# Retrieve the TCR Go executable to be launched depending on local machine's
# OS and architecture
# ------------------------------------------------------------------------------

retrieve_tcr_go_exe() {
  os_family=$(get_os_family)
  [ $? -ne 0 ] && return 1
  os_arch=$(uname -m)
  bin_ext=$(get_bin_ext ${os_family})

  # Expected TCR Go version
  expected_version=$(retrieve_expected_tcr_version)
  [ $? -ne 0 ] && return 1

  # Path to TCR Go binary file for local machine
  tcr_bin_path="${TCR_BIN_DIR}/tcr_${os_family}_${os_arch}${bin_ext}"

  file_missing=$(type "${tcr_bin_path}" >/dev/null 2>/dev/null; echo $?)
  # If the file already exists, check its current version
  version_mismatch=0
  if [ ${file_missing} -eq 0 ]; then
    current_version=$(retrieve_current_tcr_version "${tcr_bin_path}")
    version_mismatch="$( [ "${current_version}" = "${expected_version}" ]; echo $? )"
  fi

  # If the file does not exist or if versions do not match, download it from TCR GitHub repository
  if [ ${file_missing} -ne 0 ] || [ ${version_mismatch} -ne 0 ]; then
    download_tcr_go "${expected_version}" "${os_family}" "${os_arch}" "${tcr_bin_path}"
    [ $? -ne 0 ] && return 1
  fi

  echo "${tcr_bin_path}"
}

# ------------------------------------------------------------------------------
# Main
# ------------------------------------------------------------------------------

tcr_go_exe=$(retrieve_tcr_go_exe)
[ $? -ne 0 ] && trace_info "Aborting" && exit 1

# shellcheck disable=SC2086
"${tcr_go_exe}" "$@" --base-dir="${BASE_DIR}" --config-dir="${BASE_DIR}" --work-dir="${BASE_DIR}"
exit $?
