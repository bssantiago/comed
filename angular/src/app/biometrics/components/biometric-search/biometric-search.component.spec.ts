import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BiometricSearchComponent } from './biometric-search.component';
import { FormsModule } from '@angular/forms';
import { RouterTestingModule } from '@angular/router/testing';
import { HttpClientModule } from '@angular/common/http';
import { SharedModule } from '../../../shared/shared.module';
import { ToastrModule } from 'ngx-toastr';
import { Ng2CompleterModule, CompleterService } from 'ng2-completer';
import { BiometricService } from '../../services/biometric.service';
import { ToastService } from '../../../shared/services/toast.service';
import { LocalStorageService } from '../../../shared/services/local-storage.service';

describe('BiometricSearchComponent', () => {
  let component: BiometricSearchComponent;
  let fixture: ComponentFixture<BiometricSearchComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [FormsModule,
        RouterTestingModule,
        HttpClientModule,
        SharedModule,
        ToastrModule.forRoot(),
        Ng2CompleterModule],
      providers: [BiometricService, ToastService, CompleterService, LocalStorageService],
      declarations: [BiometricSearchComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BiometricSearchComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
