// app.routes.ts (sin @NgModule)
import { Routes } from '@angular/router';
import { JokesListComponent } from './pages/jokes-list/jokes-list.component';
import { CreateModifyJokeComponent } from './pages/create-modify-joke/create-modify-joke.component';
import { FlagsListComponent } from './pages/flags-list/flags-list.component';
import { PrimeraVezListComponent } from './pages/primera-vez-list/primera-vez-list.component';
import { CreateModifyPrimeraVezComponent } from './pages/create-modify-primera-vez/create-modify-primera-vez.component';
import { JokesTelevisionListComponent } from './pages/jokes-television-list/jokes-television-list.component';
import { TypesListComponent } from './pages/types-list/types-list.component';
import { LanguageListComponent } from './pages/language-list/language-list.component';
import { CategoryListComponent } from './pages/categories-list/categories-list.component';

export const routes: Routes = [
  { path: '', redirectTo: 'jokes', pathMatch: 'full' },
  { path: 'jokes', component: JokesListComponent },
  { path: 'jokes/create', component: CreateModifyJokeComponent },
  { path: 'jokes/edit/:id', component: CreateModifyJokeComponent },
  { path: 'flags', component: FlagsListComponent },
  { path: 'primera_vez', component: PrimeraVezListComponent },
  { path: 'primera_vez/create', component: CreateModifyPrimeraVezComponent },
  { path: 'primera_vez/edit/:id', component: CreateModifyPrimeraVezComponent },
  { path: 'jokes-television', component: JokesTelevisionListComponent },
  { path: 'types', component: TypesListComponent },
  { path: 'languages', component: LanguageListComponent },
  { path:'categories', component: CategoryListComponent }
];
