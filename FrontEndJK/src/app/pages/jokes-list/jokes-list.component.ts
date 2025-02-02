import { Component, OnInit, ViewChild } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { MatTableModule } from '@angular/material/table';
import { Jokes } from '../../model/jokes.model';
import { JokesService } from '../../service/jokes.service';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { Router } from '@angular/router';
import { RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { MatPaginator, MatPaginatorModule } from '@angular/material/paginator';

@Component({
  selector: 'app-jokes-list',
  standalone: true,
  imports: [MatTableModule, MatIconModule, MatButtonModule, RouterModule, FormsModule, MatPaginatorModule],
  templateUrl: './jokes-list.component.html',
  styleUrl: './jokes-list.component.css'
})
export class JokesListComponent implements OnInit {
  displayedColumns: string[] = ['id', 'language', 'text', 'category', 'flags', 'type', 'actions'];
  jokes = new MatTableDataSource<Jokes>();
  searchText: string = '';

  @ViewChild(MatPaginator) paginator!: MatPaginator;

  constructor(private jokesService: JokesService, private router: Router) {}

  ngOnInit(): void {
    this.cargarJokes();
  }

  cargarJokes(): void {
    this.jokesService.getJokes().subscribe(jokes => {
      this.jokes.data = jokes;
      this.jokes.paginator = this.paginator;
    });
  }

  getFlags(joke: Jokes): string {
    if (!joke.flagses || joke.flagses.length === 0) {
      return 'Ninguna';
    }
    return joke.flagses.map(flag => flag.flag).join(', ');
  }

  getType(joke: Jokes): string {
    return joke.types?.type || 'Desconocido';
  }

  getCategory(joke: Jokes): string {
    return joke.categories?.category || 'Sin categoría';
  }

  editJoke(joke: Jokes): void {
    console.log('Editar chiste', joke);
    this.router.navigate([`/jokes/edit/${joke.id}`]).then(() => {
      window.location.reload();
    });
  }

  applyFilter(): void {
    this.jokes.filter = this.searchText.trim().toLowerCase();
  }

  addJoke(): void {
    this.router.navigate(['jokes/create']).then(() => {
      window.location.reload();
    });
  }

  deleteJoke(jokeId: number): void {
    console.log('Eliminar chiste', jokeId);
    if (!jokeId || jokeId === undefined) {
      console.error('Error: El chiste no tiene un ID válido');
      return;
    }
  
    if (confirm(`¿Estás seguro de eliminar el chiste con ID ${jokeId}?`)) {
      this.jokesService.deleteJoke(jokeId).subscribe(
        () => {
          console.log(`Chiste con ID ${jokeId} eliminado`);
          this.cargarJokes();
        },
        error => console.error('Error al eliminar chiste:', error)
      );
    }
  }

  openFlagsPage(): void {
    this.router.navigate(['flags']).then(() => {
      window.location.reload();
    });
  }
  
  openPrimeraVezPage(): void {
    this.router.navigate(['primera_vez']).then(() => {
      window.location.reload();
    });
  }
  
  openJokesPrimeraVezPage(): void {
    this.router.navigate(['jokes-television']).then(() => {
      window.location.reload();
    });
  }

  openTypesPage(): void {
    this.router.navigate(['types']).then(() => {
      window.location.reload();
    });
  }

  openLanguagesPage(): void {
    this.router.navigate(['languages']).then(() => {
      window.location.reload();
    });
  }

  openCategoriesPage(): void {
    this.router.navigate(['categories']).then(() => {
      window.location.reload();
    });
  }

  selectJokesNoTV(): void {
    this.jokesService.getJokesWithoutPrimeraVez().subscribe(jokes => {
      this.jokes.data = jokes;
      this.jokes.paginator = this.paginator;
    });
  }
}
