import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppComponent} from './app.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {InjectableRxStompConfig, RxStompService, rxStompServiceFactory} from '@stomp/ng2-stompjs';
import {appRxStompConfig} from './app-rx-stomp.config';
import {MonitorComponent, RecordingDoneDialogComponent} from './monitor/monitor.component';
import {MenuComponent} from './menu/menu.component';
import {SetupComponent} from './setup/setup.component';
import {AppRoutingModule} from './app-routing.module';
import {MatButtonModule} from '@angular/material/button';
import {MatToolbarModule} from '@angular/material/toolbar';
import {SimulatorComponent} from './simulator/simulator.component';
import {SimulatorLedComponent} from './simulator/simulator-led.component';
import {SimulatorControlComponent} from './simulator/simulator-control.component';
import {HttpClientModule} from '@angular/common/http';
import {SimulatorService} from './simulator/simulator.service';
import {SceneService} from './scene/scene.service';
import {AboutComponent} from './about/about.component';
import {MatSlideToggleModule} from '@angular/material/slide-toggle';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatSelectModule} from '@angular/material/select';
import {MatCheckboxModule} from '@angular/material/checkbox';
import {ReactiveFormsModule} from '@angular/forms';
import {MatRadioModule} from '@angular/material/radio';
import {MatSliderModule} from '@angular/material/slider';
import { MatTableModule } from '@angular/material/table';
import {MatIconModule} from '@angular/material/icon'
import { ListSavedScenesComponent, ConfirmDeleteDialogComponent, EditSavedSceneDialogComponent } from './list-saved-scenes/list-saved-scenes.component';
import { ScenesService } from './list-saved-scenes/scenes.service';
import { RecordComponent } from './record/record.component';
import {MatDialogModule} from '@angular/material/dialog';
import {MatInputModule} from '@angular/material/input';
import { FormsModule } from '@angular/forms';
import { SettingsComponent } from './settings/settings.component';

@NgModule({
  declarations: [
    AppComponent,
    MonitorComponent,
    MenuComponent,
    SetupComponent,
    SimulatorComponent,
    SimulatorLedComponent,
    SimulatorControlComponent,
    AboutComponent,
    ListSavedScenesComponent,
    RecordComponent,
    ConfirmDeleteDialogComponent,
    EditSavedSceneDialogComponent,
    SettingsComponent,
    RecordingDoneDialogComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    HttpClientModule,
    MatButtonModule,
    MatToolbarModule,
    MatIconModule,
    AppRoutingModule,
    MatSlideToggleModule,
    MatFormFieldModule,
    MatSelectModule,
    MatCheckboxModule,
    MatRadioModule,
    MatSliderModule,
    MatTableModule,
    ReactiveFormsModule,
    MatDialogModule,
    MatInputModule,
    FormsModule
  ],
  entryComponents: [
    ConfirmDeleteDialogComponent,
    RecordingDoneDialogComponent
  ],
  providers: [
    SceneService,
    ScenesService,
    SimulatorService,
    {
      provide: InjectableRxStompConfig,
      useValue: appRxStompConfig
    },
    {
      provide: RxStompService,
      useFactory: rxStompServiceFactory,
      deps: [InjectableRxStompConfig]
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
