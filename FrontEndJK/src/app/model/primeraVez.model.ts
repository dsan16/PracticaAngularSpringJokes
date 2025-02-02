import { Jokes } from './jokes.model';
import { Telefono } from './telefono.model';

export interface PrimeraVez {
  id?: number;
  programa: string;
  fechaEmision: string;
  joke: Jokes;
  telefonos: Telefono[];
}
