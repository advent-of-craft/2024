from django.urls import path

from .views import create_reindeer, get_reindeer

urlpatterns = [
    path('reindeer', create_reindeer, name='create_reindeer'),
    path('reindeer/<uuid:id>', get_reindeer, name='get_reindeer'),
]
