import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Language } from '../../model/language.model';
import { CommonModule } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import { FormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIcon } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';

@Component({
  selector: 'app-language-dialog',
  standalone: true,
  imports: [MatFormFieldModule, FormsModule, MatButtonModule, CommonModule, MatInputModule],
  templateUrl: './language-dialog.component.html',
  styleUrls: ['./language-dialog.component.css']
})
export class LanguageDialogComponent {
  constructor(
    public dialogRef: MatDialogRef<LanguageDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: Language
  ) {}

  save(): void {
    if (!this.data.code.trim() || !this.data.language.trim()) {
      alert('Todos los campos son obligatorios.');
      return;
    }
    if(this.data.code.length > 2) {
      alert('El código del lenguaje no puede tener más de dos caracteres.');
      return;
    }
    this.dialogRef.close(this.data);
  }

  cancel(): void {
    this.dialogRef.close();
  }
}
