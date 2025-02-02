import { PrimeraVez } from './primeraVez.model';

export interface Telefono {
    id?: number;
    numero: string;
    primeraVez?: PrimeraVez; // Opcional para evitar referencias circulares
  }
  