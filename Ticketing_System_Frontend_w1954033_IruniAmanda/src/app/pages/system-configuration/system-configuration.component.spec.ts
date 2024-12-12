import { ComponentFixture, TestBed } from '@angular/core/testing';

// @ts-ignore
import { SystemConigurationComponent } from './system-coniguration.component';

describe('SystemConigurationComponent', () => {
  let component: SystemConigurationComponent;
  let fixture: ComponentFixture<SystemConigurationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SystemConigurationComponent]
    })
      .compileComponents();

    fixture = TestBed.createComponent(SystemConigurationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
