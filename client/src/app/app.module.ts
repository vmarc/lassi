import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppComponent} from './app.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {InjectableRxStompConfig, RxStompService, rxStompServiceFactory} from '@stomp/ng2-stompjs';
import {appRxStompConfig} from './app-rx-stomp.config';
import {MonitorComponent, RecordingDoneDialogComponent} from './monitor/monitor.component';
import {MenuComponent} from './menu/menu.component';
import {AppRoutingModule} from './app-routing.module';
import {MatButtonModule} from '@angular/material/button';
import {MatToolbarModule} from '@angular/material/toolbar';
import {SimulatorComponent} from './simulator/simulator.component';
import {SimulatorLedComponent} from './simulator/simulator-led.component';
import {SimulatorControlComponent} from './simulator/simulator-control.component';
import {HttpClientModule} from '@angular/common/http';
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
import { ListSavedScenesComponent, ConfirmDeleteDialogComponent, EditSavedSceneDialogComponent, SceneDetailsDialogComponent } from './list-saved-scenes/list-saved-scenes.component';
import { ScenesService } from './list-saved-scenes/scenes.service';
import {MatDialogModule} from '@angular/material/dialog';
import {MatInputModule} from '@angular/material/input';
import { MatPaginatorModule } from '@angular/material/paginator';
import { FormsModule } from '@angular/forms';
import { SettingsComponent } from './settings/settings.component';

@NgModule({
  declarations: [
    AppComponent,
    MonitorComponent,
    MenuComponent,
    SimulatorComponent,
    SimulatorLedComponent,
    SimulatorControlComponent,
    AboutComponent,
    ListSavedScenesComponent,
    ConfirmDeleteDialogComponent,
    EditSavedSceneDialogComponent,
    SceneDetailsDialogComponent,
    SettingsComponent,
    RecordingDoneDialogComponent,
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
    MatPaginatorModule,
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
    ScenesService,
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
