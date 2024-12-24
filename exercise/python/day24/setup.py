from setuptools import setup

setup(
    name='Toy Delivery',
    version='0.1',
    install_requires=[
        'assertpy',
        'faker',
        'hypothesis',
        'pytest'
    ],
    test_suite='tests',
)
