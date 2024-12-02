import hypothesis
from setuptools import setup, find_packages

setup(
    name='FizzBuzz',
    version='0.1',
    packages=find_packages(),
    install_requires=[
        hypothesis
    ],
    test_suite='tests',
)
