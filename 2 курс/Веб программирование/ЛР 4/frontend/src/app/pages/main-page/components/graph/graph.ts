import {ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {PointService} from '../../../../services/point.service';
import {RService} from '../../../../services/r.service';
import {CommonModule} from '@angular/common';
import {MessageService} from '../../../../services/message.service';

@Component({
  selector: 'app-graph',
  imports: [CommonModule],
  templateUrl: './graph.html',
  styleUrl: './graph.css',
})
export class Graph implements OnInit {
  rValue = 1;
  points: any[] = [];
  constructor(private http: HttpClient, private pointService: PointService, private cdr: ChangeDetectorRef, private rService: RService, private messageService: MessageService) {
  }

  ngOnInit() {
    this.rService.r$.subscribe(v => this.rValue = v);

    this.pointService.pointAdded$.subscribe((point) => {
      this.points.push(point);
      this.cdr.detectChanges();
    });

    this.pointService.pointsCleared$.subscribe(() => {
      this.points = [];
      this.cdr.detectChanges();
    });

    this.loadExistingPoints();
  }

  loadExistingPoints() {
    this.http.get<any[]>("http://localhost:8080/backend/app/main/load")
      .subscribe({
        next: (points) => {
          this.points = points;
          this.cdr.detectChanges();
        },
        error: (err) => {
          console.error('Ошибка загрузки точек:', err);
        }
      });
  }

  getCx(x: number): number {
    return 200 + (x / this.rValue) * 150;
  }

  getCy(y: number): number {
    return 200 - (y / this.rValue) * 150;
  }

  onSvgClick(event: MouseEvent) {
    const svg = event.currentTarget as SVGElement;
    const rect = svg.getBoundingClientRect();

    const svgX = event.clientX - rect.left;
    const svgY = event.clientY - rect.top;

    const centerX = rect.width / 2;
    const centerY = rect.height / 2;

    const scale = rect.width * (150 / 400);

    const mathX = ((svgX - centerX) / scale) * this.rValue;
    const mathY = ((-(svgY - centerY)) / scale) * this.rValue;

    const requestBody = {
      x: mathX,
      y: mathY,
      r: this.rValue
    };

    this.http.post<any>("http://localhost:8080/backend/app/main/check", requestBody)
      .subscribe({
        next: (response) => {
          this.pointService.addPoint(response);
          this.messageService.changeMessage("Точка успешно добавлена");
        },
        error: (err) => {
          console.error(err);
          this.messageService.changeMessage("Ошибка при добавлении точки");
        }
      });
  }
}
