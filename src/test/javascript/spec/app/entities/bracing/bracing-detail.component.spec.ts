/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { ApdSampleApplicationTestModule } from '../../../test.module';
import { BracingDetailComponent } from '../../../../../../main/webapp/app/entities/bracing/bracing-detail.component';
import { BracingService } from '../../../../../../main/webapp/app/entities/bracing/bracing.service';
import { Bracing } from '../../../../../../main/webapp/app/entities/bracing/bracing.model';

describe('Component Tests', () => {

    describe('Bracing Management Detail Component', () => {
        let comp: BracingDetailComponent;
        let fixture: ComponentFixture<BracingDetailComponent>;
        let service: BracingService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ApdSampleApplicationTestModule],
                declarations: [BracingDetailComponent],
                providers: [
                    BracingService
                ]
            })
            .overrideTemplate(BracingDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(BracingDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BracingService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Bracing(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.bracing).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
