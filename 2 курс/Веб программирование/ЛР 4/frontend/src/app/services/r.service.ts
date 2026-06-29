import { Injectable } from '@angular/core';
import {BehaviorSubject} from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class RService {
  private rSubject = new BehaviorSubject<number>(1);
  r$ = this.rSubject.asObservable();

  setR(value: number) {
    this.rSubject.next(value);
  }
}
