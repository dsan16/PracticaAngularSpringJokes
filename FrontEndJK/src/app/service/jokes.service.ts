import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import exp from 'constants';
import { Jokes } from '../model/jokes.model';

@Injectable({
    providedIn: 'root'
})
export class JokesService {
    private url = 'http://localhost:8080/api/jokes';

    constructor(private http: HttpClient) {}

    getJokes(): Observable<Jokes[]> {
        return this.http.get<Jokes[]>(this.url);
    }

    deleteJoke(id: number): Observable<void> {
        return this.http.delete<void>(`${this.url}/${id}`);
    }

    editJoke(joke: Jokes): Observable<Jokes> {
        return this.http.put<Jokes>(`${this.url}/${joke.id}`, joke);
    }

    createJoke(joke: Jokes): Observable<Jokes> {
        console.log("➡ Enviando petición a la API:", joke);
        return this.http.post<Jokes>(this.url, joke);
    }

    getJokeById(id: number): Observable<Jokes> {
        return this.http.get<Jokes>(`${this.url}/${id}`);
    }

    getJokesWithoutPrimeraVez(): Observable<Jokes[]> {
        return this.http.get<Jokes[]>(`${this.url}/without_tv`);
    }
      
}