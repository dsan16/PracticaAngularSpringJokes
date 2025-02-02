import { Component } from '@angular/core';
import { Jokes } from '../../model/jokes.model';
import { PrimeraVez } from '../../model/primeraVez.model';
import { JokesService } from '../../service/jokes.service';
import { PrimeraVezService } from '../../service/primeraVez.service';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { MatIcon } from '@angular/material/icon';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-create-modify-primera-vez',
  standalone: true,
  imports: [FormsModule, CommonModule, MatIcon],
  templateUrl: './create-modify-primera-vez.component.html',
  styleUrl: './create-modify-primera-vez.component.css'
})
export class CreateModifyPrimeraVezComponent {
  primeraVez: PrimeraVez = {
    programa: '',
    fechaEmision: '',
    joke: {} as Jokes,
    telefonos: []
  };

  isEditMode = false;
  jokesList: Jokes[] = [];

  constructor(
    private jokesService: JokesService,
    private primeraVezService: PrimeraVezService,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.loadJokes();

    const idParam = this.route.snapshot.paramMap.get('id');
    if(idParam){
      this.isEditMode = true;
      const id = +idParam;
      this.primeraVezService.getPrimeraVezById(id).subscribe({
        next: (existingPrimeraVez) => {
          this.primeraVez = existingPrimeraVez;
        },
        error: (error) => {
          console.error('Error al cargar la Primera Vez', error);
        }
      });
    }
    else{
      this.isEditMode = false;
    }
  }

  loadJokes(): void {
    this.jokesService.getJokes().subscribe(data => {
      this.jokesList = data;
    });
  }

  addTelefono(): void {
    this.primeraVez.telefonos.push({ numero: '' });
  }

  removeTelefono(index: number): void {
    this.primeraVez.telefonos.splice(index, 1);
  }

  submitForm(event: Event): void {
    event.preventDefault();

    if (!this.primeraVez.fechaEmision) {
      alert('Debes seleccionar una fecha de emisión.');
      return;
    }

    if (!this.primeraVez.telefonos || this.primeraVez.telefonos.length === 0) {
      alert('Debes introducir un teléfono al menos.');
      return;
    }

    if (this.isEditMode) {
      this.primeraVezService.update(this.primeraVez).subscribe({
        next: (response) => {
          console.log("Primera vez actualizado:", response);
          alert("✅ Primera vez actualizado con éxito!");
        },
        error: (error) => {
          console.error("❌ Error al actualizar primera vez:", error);
          alert("❌ No se pudo actualizar primera vez.");
        }
      });
    } else {
      this.primeraVezService.create(this.primeraVez).subscribe({
        next: (response) => {
          console.log("Primera vez guardado:", response);
          alert("Primera vez agregado con éxito!");
        },
        error: (error) => {
          console.error("❌ Error al agregar primera vez:", error);
          alert("❌ No se pudo agregar primera vez. Inténtalo de nuevo.");
        }
      });
    }
  }

  goBack(): void {
    window.history.back();
  }
}
