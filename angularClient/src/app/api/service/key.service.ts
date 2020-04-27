import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { saveAs } from 'file-saver';
import { ParamsInput } from '../entity/paramsInput';
import { tap, catchError } from 'rxjs/operators';

const apiUrl = 'http://localhost:8080/api/key';

@Injectable({
  providedIn: 'root',
})
export class KeyService {
  constructor(private http: HttpClient) {}

  getKeys() {
    return this.http.get(`${apiUrl}`).pipe(
      tap((_) => this.log('fetching Keys')),
      catchError(this.handleError('getKeys', []))
    );
  }

  createNewKey(key: ParamsInput) {
    return this.http.post(`${apiUrl}/create`, key).pipe(
      tap((_) => this.log('creating new Key')),
      catchError(this.handleError('createNewKey', []))
    );
  }

  // downloadKey(keyFileId: number) {
  //   return this.http
  //     .get(`${apiUrl}/download/${keyFileId}`, {
  //       responseType: 'blob',
  //       observe: 'response',
  //     })
  //     .subscribe((res: HttpResponse<Blob>) => {
  //       const fileName = this.getFileNameFromResponse(res);
  //       this.saveFile(res.body, fileName, res.body.type);
  //     });
  // }

  // saveFile(blobContent: Blob, fileName: string, fileType: string) {
  //   const blob = new Blob([blobContent], { type: fileType });
  //   saveAs(blobContent, fileName);
  // }

  getFileNameFromResponse(res) {
    const contentDispostion = res.headers.get('content-disposition');
    const matches = /filename="([^;]+)"/gi.exec(contentDispostion);
    const filename = (matches[1] || 'untitled').trim();
    return filename;
  }

  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      console.error(error);
      this.log(`${operation} failed: ${error.message}`);

      return of(result as T);
    };
  }

  private log(message: string) {
    console.log(message);
  }
}
