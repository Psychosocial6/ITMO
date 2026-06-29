import {Component, OnInit, OnDestroy, ChangeDetectorRef} from '@angular/core';
import { CommonModule } from '@angular/common';
import { Subscription } from 'rxjs';
import { PointService } from '../../../../services/point.service';

@Component({
  selector: 'app-table',
  imports: [CommonModule],
  templateUrl: './table.html',
  styleUrl: './table.css',
})
export class Table implements OnInit, OnDestroy {
  rows: any[] = [];
  private pointAddedSub!: Subscription;
  private pointsClearedSub!: Subscription;

  constructor(private pointService: PointService, private cdr: ChangeDetectorRef) {}

  ngOnInit() {
    this.pointService.loadHistory().subscribe(data => {
      this.rows = data.map((item, index) => ({
        id: index + 1,
        x: item.x,
        y: item.y,
        r: item.r,
        result: item.result ? 'Попадание' : 'Промах',
        requestTime: item.requestTime,
        localTime: item.localTime
      }));
      this.cdr.detectChanges();
    });

    this.pointAddedSub = this.pointService.pointAdded$.subscribe(newPoint => {
      this.rows.push({
        id: this.rows.length + 1,
        x: newPoint.x,
        y: newPoint.y,
        r: newPoint.r,
        result: newPoint.result ? 'Попадание' : 'Промах',
        requestTime: newPoint.requestTime,
        localTime: newPoint.localTime
      });
      this.cdr.detectChanges();
    });

    this.pointsClearedSub = this.pointService.pointsCleared$.subscribe(() => {
      this.rows = [];
      this.cdr.detectChanges();
    });
  }

  ngOnDestroy() {
    if (this.pointAddedSub) this.pointAddedSub.unsubscribe();
    if (this.pointsClearedSub) this.pointsClearedSub.unsubscribe();
  }
}
