import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ListSavedScenesComponent } from './list-saved-scenes.component';

describe('ListSavedScenesComponent', () => {
  let component: ListSavedScenesComponent;
  let fixture: ComponentFixture<ListSavedScenesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ListSavedScenesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ListSavedScenesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
