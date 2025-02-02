import { Categories } from './categories.model';
import { Language } from './language.model';
import { Types } from './types.model';
import { Flags } from './flags.model';
import { PrimeraVez } from './primeraVez.model';

export interface Jokes {
  id?: number;
  categories: Categories;
  language: Language;
  types: Types;
  text1: string;
  text2?: string;
  flagses: Flags[];
  primeraVez?: PrimeraVez;
}
