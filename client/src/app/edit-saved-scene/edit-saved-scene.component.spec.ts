import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EditSavedSceneComponent } from './edit-saved-scene.component';

describe('EditSavedSceneComponent', () => {
  let component: EditSavedSceneComponent;
  let fixture: ComponentFixture<EditSavedSceneComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EditSavedSceneComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EditSavedSceneComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
