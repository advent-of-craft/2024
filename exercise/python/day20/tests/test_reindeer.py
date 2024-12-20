from uuid import uuid4

import pytest
from django.urls import reverse
from rest_framework import status
from rest_framework.test import APIClient

client = APIClient()


@pytest.mark.django_db
def test_should_get_reindeer():
    response = client.get(reverse('get_reindeer', args=['40f9d24d-d3e0-4596-adc5-b4936ff84b19']))
    assert response.status_code == status.HTTP_200_OK


@pytest.mark.django_db
def test_not_found_for_not_existing_reindeer():
    non_existing_reindeer = str(uuid4())
    response = client.get(reverse('get_reindeer', args=[non_existing_reindeer]))
    assert response.status_code == status.HTTP_404_NOT_FOUND


@pytest.mark.django_db
def test_conflict_when_trying_to_create_existing_one():
    payload = {"name": "Petar", "color": 2}
    response = client.post(reverse('create_reindeer'), payload)
    assert response.status_code == status.HTTP_409_CONFLICT
