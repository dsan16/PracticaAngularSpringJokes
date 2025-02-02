import { Jokes } from "./jokes.model";

export interface Flags{
    id: number;
    flag: string;
    jokeses: Jokes[];
}