import {HttpClientModule} from '@angular/common/http';
import {NgModule} from '@angular/core';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {MatButtonModule} from '@angular/material/button';
import {MatCheckboxModule} from '@angular/material/checkbox';
import {MatDialogModule} from '@angular/material/dialog';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatIconModule} from '@angular/material/icon';
import {MatInputModule} from '@angular/material/input';
import {MatPaginatorModule} from '@angular/material/paginator';
import {MatRadioModule} from '@angular/material/radio';
import {MatSelectModule} from '@angular/material/select';
import {MatSlideToggleModule} from '@angular/material/slide-toggle';
import {MatSliderModule} from '@angular/material/slider';
import {MatSnackBarModule} from '@angular/material/snack-bar';
import {MatSortModule} from '@angular/material/sort';
import {MatTableModule} from '@angular/material/table';
import {MatToolbarModule} from '@angular/material/toolbar';
import {MatTooltipModule} from '@angular/material/tooltip';
import {BrowserModule} from '@angular/platform-browser';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {InjectableRxStompConfig, RxStompService, rxStompServiceFactory} from '@stomp/ng2-stompjs';
import {AboutComponent} from './about/about.component';
import {AppRoutingModule} from './app-routing.module';
import {appRxStompConfig} from './app-rx-stomp.config';
import {AppComponent} from './app.component';
import {MenuComponent} from './menu/menu.component';
import {MonitorComponent} from './monitor/monitor.component';
import {OldSimulatorControlComponent} from './old-simulator/old-simulator-control.component';
import {OldSimulatorLedComponent} from './old-simulator/old-simulator-led.component';
import {OldSimulatorComponent} from './old-simulator/old-simulator.component';
import {ConfirmDeleteDialogComponent} from './scene/confirm-delete-dialog.component';
import {SceneDetailsComponent} from './scene/scene-details.component';
import {SceneEditComponent} from './scene/scene-edit.component';
import {ScenesComponent} from './scene/scenes.component';
import {ScenesService} from './scene/scenes.service';
import {SettingsComponent} from './settings/settings.component';
import {SimulatorControlComponent} from './simulator/simulator-control.component';
import {SimulatorComponent} from './simulator/simulator.component';

@NgModule({
  declarations: [
    AppComponent,
    MonitorComponent,
    MenuComponent,
    OldSimulatorComponent,
    OldSimulatorLedComponent,
    OldSimulatorControlComponent,
    AboutComponent,
    ScenesComponent,
    ConfirmDeleteDialogComponent,
    SceneEditComponent,
    SceneDetailsComponent,
    SettingsComponent,
    SimulatorComponent,
    SimulatorControlComponent,
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
    FormsModule,
    MatTooltipModule,
    MatSnackBarModule,
    MatSortModule,
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
