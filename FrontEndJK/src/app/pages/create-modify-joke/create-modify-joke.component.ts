import { Component, NgModule, OnInit } from '@angular/core';
import { JokesService } from '../../service/jokes.service';
import { CategoriesService } from '../../service/categories.service';
import { LanguagesService } from '../../service/language.service';
import { TypesService } from '../../service/types.service';
import { FlagsService } from '../../service/flags.service';
import { Jokes } from '../../model/jokes.model';
import { Categories } from '../../model/categories.model';
import { Language } from '../../model/language.model';
import { Types } from '../../model/types.model';
import { Flags } from '../../model/flags.model';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-create-modify-joke',
  standalone: true,
  imports: [FormsModule, CommonModule, ReactiveFormsModule],
  templateUrl: './create-modify-joke.component.html',
  styleUrls: ['./create-modify-joke.component.css']
})
export class CreateModifyJokeComponent implements OnInit {

  joke: Jokes = {
    text1: '',
    text2: '',
    categories: {} as Categories,
    language: {} as Language,
    types: {} as Types,
    flagses: []
  };

  isEditMode = false;

  categoriesList: Categories[] = [];
  languagesList: Language[] = [];
  typesList: Types[] = [];
  flagsList: Flags[] = [];
  selectedFlags: Flags[] = [];

  constructor(
    private route: ActivatedRoute,
    private jokesService: JokesService,
    private categoriesService: CategoriesService,
    private languagesService: LanguagesService,
    private typesService: TypesService,
    private flagsService: FlagsService
  ) {}

  ngOnInit(): void {
    this.loadDropdowns();

    const idParam = this.route.snapshot.paramMap.get('id');
    if (idParam) {
      this.isEditMode = true;
      const id = +idParam;
      this.jokesService.getJokeById(id).subscribe({
        next: (existingJoke) => {
          this.joke = existingJoke;
          this.selectedFlags = [...(existingJoke.flagses || [])];
        },
        error: (err) => {
          console.error('Error al cargar chiste para edición', err);
        }
      });
    } else {
      this.isEditMode = false;
    }
  }

  loadDropdowns(): void {
    this.categoriesService.getCategories().subscribe(data => this.categoriesList = data);
    this.languagesService.getLanguages().subscribe(data => this.languagesList = data);
    this.typesService.getTypes().subscribe(data => this.typesList = data);
    this.flagsService.getFlags().subscribe(data => this.flagsList = data);
  }

  onFlagChange(flag: Flags, event: Event): void {
    const checked = (event.target as HTMLInputElement).checked;
    if (checked) {
      this.selectedFlags.push(flag);
    } else {
      this.selectedFlags = this.selectedFlags.filter(f => f.id !== flag.id);
    }
  }

  isFlagSelected(flag: Flags): boolean {
    return this.selectedFlags.some(f => f.id === flag.id);
  }

  compareCategories(c1: Categories, c2: Categories): boolean {
    return c1 && c2 && c1.id === c2.id;
  }
  
  compareLanguages(l1: Language, l2: Language): boolean {
    return l1 && l2 && l1.id === l2.id;
  }
  
  compareTypes(t1: Types, t2: Types): boolean {
    return t1 && t2 && t1.id === t2.id;
  }
  
  private validateTypeAndText2(): boolean {
    const currentType = this.joke.types?.type || '';
  
    if (currentType === 'onepart' && this.joke.text2?.trim()) {
      alert('No se permite texto 2 si el chiste es de tipo "onepart".');
      return false;
    }
  
    if (currentType === 'twopart' && (!this.joke.text2 || !this.joke.text2.trim())) {
      alert('Debes proporcionar texto 2 si el chiste es de tipo "twopart".');
      return false;
    }
  
    return true;
  }

  submitForm(event: Event): void {
    event.preventDefault();

    if (!this.validateTypeAndText2()) {
      return;
    }

    this.joke.flagses = this.selectedFlags;
    

    if (this.isEditMode) {
      this.jokesService.editJoke(this.joke).subscribe({
        next: (response) => {
          console.log("✅ Chiste actualizado:", response);
          alert("✅ Chiste actualizado con éxito!");
        },
        error: (error) => {
          console.error("❌ Error al actualizar el chiste:", error);
          alert("❌ No se pudo actualizar el chiste.");
        }
      });
    } else {
      this.jokesService.createJoke(this.joke).subscribe({
        next: (response) => {
          console.log("✅ Chiste guardado:", response);
          alert("✅ Chiste agregado con éxito!");
        },
        error: (error) => {
          console.error("❌ Error al agregar el chiste:", error);
          alert("❌ No se pudo agregar el chiste. Inténtalo de nuevo.");
        }
      });
    }
  }

  goBack(): void {
    window.history.back();
  }
}
