import assertpy
from setuptools import setup, find_packages

setup(
    name='Rock Paper Scissors Lizard Spock',
    version='0.1',
    packages=find_packages(),
    install_requires=[
        assertpy
    ],
    test_suite='features',  # run them with behave
)
