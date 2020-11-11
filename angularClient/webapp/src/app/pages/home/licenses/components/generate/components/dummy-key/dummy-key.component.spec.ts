import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DummyKeyComponent } from './dummy-key.component';

describe('DummyKeyComponent', () => {
  let component: DummyKeyComponent;
  let fixture: ComponentFixture<DummyKeyComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DummyKeyComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DummyKeyComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
