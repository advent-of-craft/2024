from django.db import models
import uuid


class Reindeer(models.Model):
    id = models.UUIDField(primary_key=True, default=uuid.uuid4, editable=False)
    name = models.CharField(max_length=255)
    color = models.IntegerField()

    class ReindeerColor(models.IntegerChoices):
        WHITE = 0
        BLACK = 1
        PURPLE = 2
