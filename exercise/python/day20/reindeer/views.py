from django.http import JsonResponse
from rest_framework import status
from rest_framework.decorators import api_view
from rest_framework.response import Response

from reindeer.service import ReindeerService, ReindeerErrorCode

reindeer_service = ReindeerService()


@api_view(['POST'])
def create_reindeer(request):
    name = request.data.get('name')
    color = request.data.get('color')
    result = reindeer_service.create(name, color)

    if result == ReindeerErrorCode.ALREADY_EXIST:
        return Response({"detail": "Reindeer already exists"}, status=status.HTTP_409_CONFLICT)
    return Response(result, status=status.HTTP_201_CREATED)


@api_view(['GET'])
def get_reindeer(request, id):
    result = reindeer_service.get(id)

    if result is None:
        return Response({"detail": "Reindeer not found"}, status=status.HTTP_404_NOT_FOUND)
    return JsonResponse({"id": result.id, "name": result.name, "color": result.color})
