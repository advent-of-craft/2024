import express from 'express';
import {ReindeerService, ReindeerToCreate} from './service';
import {fold} from 'fp-ts/Either';
import {ReindeerErrorCode} from "./types";

export const app = express();
app.use(express.json());

const reindeerService = new ReindeerService();

app.get('/reindeer/:id', (req, res) => {
    const result = reindeerService.get(req.params.id);
    fold(
        (error) => {
            if (error === ReindeerErrorCode.NotFound) {
                res.status(404).send('Reindeer not found');
            }
        },
        (reindeer) => res.status(200).json(reindeer)
    )(result);
});

app.post('/reindeer', (req, res) => {
    const {name, color} = req.body;
    const reindeerToCreate: ReindeerToCreate = {name, color};
    const result = reindeerService.create(reindeerToCreate);
    fold(
        (error) => {
            if (error === ReindeerErrorCode.AlreadyExist) {
                res.status(409).send('Reindeer already exists');
            }
        },
        (reindeer) => res.status(201).json(reindeer)
    )(result);
});

const port = 3000;
app.listen(port, () => {
    console.log(`Server is running on http://localhost:${port}`);
});