import { Injectable } from '@angular/core';
import {Observable, Subject} from 'rxjs';
import {HttpClient} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class PointService {
  private pointSubject = new Subject<any>();
  private clearPointsSubject = new Subject<void>();

  pointAdded$ = this.pointSubject.asObservable();
  pointsCleared$ = this.clearPointsSubject.asObservable();

  constructor(private http: HttpClient) {}

  addPoint(pointData: any) {
    this.pointSubject.next(pointData);
  }

  clearPoints() {
    this.clearPointsSubject.next();
  }

  loadHistory(): Observable<any[]> {
    return this.http.get<any[]>("http://localhost:8080/backend/app/main/load");
  }
}
