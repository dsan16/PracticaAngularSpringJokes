import { Component, OnInit } from '@angular/core';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { LanguagesService } from '../../service/language.service';
import { MatDialog } from '@angular/material/dialog';
import { LanguageDialogComponent } from '../language-dialog/language-dialog.component';
import { Language } from '../../model/language.model';
import { MatFormFieldModule } from '@angular/material/form-field';
import { FormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { CommonModule } from '@angular/common';
import { MatIcon } from '@angular/material/icon';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-language-list',
  standalone: true,
  imports: [MatFormFieldModule, FormsModule, MatButtonModule, CommonModule, MatIcon, MatTableModule, RouterModule],
  templateUrl: './language-list.component.html',
  styleUrls: ['./language-list.component.css']
})
export class LanguageListComponent {
  displayedColumns: string[] = ['id', 'code', 'language', 'actions'];
  languagesDataSource = new MatTableDataSource<Language>();
  searchText = '';

  constructor(private languagesService: LanguagesService, private dialog: MatDialog) {}

  ngOnInit(): void {
    this.loadLanguages();
  }

  loadLanguages(): void {
    this.languagesService.getLanguages().subscribe({
      next: (languages) => {
        this.languagesDataSource.data = languages;
      },
      error: (err) => {
        console.error('Error al cargar los lenguajes:', err);
      }
    });
  }

  applyFilter(): void {
    this.languagesDataSource.filter = this.searchText.trim().toLowerCase();
  }

  addLanguage(): void {
    const dialogRef = this.dialog.open(LanguageDialogComponent, {
      width: '400px',
      data: { id: null, code: '', language: '' }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.languagesService.createLanguage(result).subscribe(() => {
          this.loadLanguages();
        });
      }
    });
  }

  editLanguage(language: Language): void {
    const dialogRef = this.dialog.open(LanguageDialogComponent, {
      width: '400px',
      data: { ...language }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.languagesService.updateLanguage(result).subscribe(() => {
          this.loadLanguages();
        });
      }
    });
  }

  deleteLanguage(languageId: number): void {
    if (confirm(`¿Estás seguro de eliminar el lenguaje con ID ${languageId}?`)) {
      this.languagesService.deleteLanguage(languageId).subscribe(() => {
        this.loadLanguages();
      });
    }
  }

  goBack(): void {
    window.history.back();
  }
}
