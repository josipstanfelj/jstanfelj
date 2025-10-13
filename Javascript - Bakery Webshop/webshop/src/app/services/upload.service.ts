import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class UploadService {
  private uploadUrl = 'http://localhost:3000/api/upload';

  constructor(private http: HttpClient) {}

  uploadImage(file: File) {
    const formData = new FormData();
    formData.append('image', file); // Ključ 'image' mora odgovarati backend ruti

    return this.http.post<{ message: string; url: string }>(this.uploadUrl, formData);
  }
}
