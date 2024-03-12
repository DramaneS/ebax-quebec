import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { IEntreprise } from 'app/entities/entreprise/entreprise.model';
import { EntrepriseService } from 'app/entities/entreprise/service/entreprise.service';
import { IProject } from 'app/entities/project/project.model';
import { ProjectService } from 'app/entities/project/service/project.service';
import { IService } from '../service.model';
import { ServiceService } from '../service/service.service';
import { ServiceFormService } from './service-form.service';

import { ServiceUpdateComponent } from './service-update.component';

describe('Service Management Update Component', () => {
  let comp: ServiceUpdateComponent;
  let fixture: ComponentFixture<ServiceUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let serviceFormService: ServiceFormService;
  let serviceService: ServiceService;
  let entrepriseService: EntrepriseService;
  let projectService: ProjectService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), ServiceUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(ServiceUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ServiceUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    serviceFormService = TestBed.inject(ServiceFormService);
    serviceService = TestBed.inject(ServiceService);
    entrepriseService = TestBed.inject(EntrepriseService);
    projectService = TestBed.inject(ProjectService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Entreprise query and add missing value', () => {
      const service: IService = { id: 456 };
      const entreprise: IEntreprise = { id: 9783 };
      service.entreprise = entreprise;

      const entrepriseCollection: IEntreprise[] = [{ id: 14242 }];
      jest.spyOn(entrepriseService, 'query').mockReturnValue(of(new HttpResponse({ body: entrepriseCollection })));
      const additionalEntreprises = [entreprise];
      const expectedCollection: IEntreprise[] = [...additionalEntreprises, ...entrepriseCollection];
      jest.spyOn(entrepriseService, 'addEntrepriseToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ service });
      comp.ngOnInit();

      expect(entrepriseService.query).toHaveBeenCalled();
      expect(entrepriseService.addEntrepriseToCollectionIfMissing).toHaveBeenCalledWith(
        entrepriseCollection,
        ...additionalEntreprises.map(expect.objectContaining),
      );
      expect(comp.entreprisesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Project query and add missing value', () => {
      const service: IService = { id: 456 };
      const projects: IProject[] = [{ id: 12130 }];
      service.projects = projects;

      const projectCollection: IProject[] = [{ id: 22869 }];
      jest.spyOn(projectService, 'query').mockReturnValue(of(new HttpResponse({ body: projectCollection })));
      const additionalProjects = [...projects];
      const expectedCollection: IProject[] = [...additionalProjects, ...projectCollection];
      jest.spyOn(projectService, 'addProjectToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ service });
      comp.ngOnInit();

      expect(projectService.query).toHaveBeenCalled();
      expect(projectService.addProjectToCollectionIfMissing).toHaveBeenCalledWith(
        projectCollection,
        ...additionalProjects.map(expect.objectContaining),
      );
      expect(comp.projectsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const service: IService = { id: 456 };
      const entreprise: IEntreprise = { id: 12546 };
      service.entreprise = entreprise;
      const project: IProject = { id: 24821 };
      service.projects = [project];

      activatedRoute.data = of({ service });
      comp.ngOnInit();

      expect(comp.entreprisesSharedCollection).toContain(entreprise);
      expect(comp.projectsSharedCollection).toContain(project);
      expect(comp.service).toEqual(service);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IService>>();
      const service = { id: 123 };
      jest.spyOn(serviceFormService, 'getService').mockReturnValue(service);
      jest.spyOn(serviceService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ service });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: service }));
      saveSubject.complete();

      // THEN
      expect(serviceFormService.getService).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(serviceService.update).toHaveBeenCalledWith(expect.objectContaining(service));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IService>>();
      const service = { id: 123 };
      jest.spyOn(serviceFormService, 'getService').mockReturnValue({ id: null });
      jest.spyOn(serviceService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ service: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: service }));
      saveSubject.complete();

      // THEN
      expect(serviceFormService.getService).toHaveBeenCalled();
      expect(serviceService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IService>>();
      const service = { id: 123 };
      jest.spyOn(serviceService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ service });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(serviceService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareEntreprise', () => {
      it('Should forward to entrepriseService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(entrepriseService, 'compareEntreprise');
        comp.compareEntreprise(entity, entity2);
        expect(entrepriseService.compareEntreprise).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareProject', () => {
      it('Should forward to projectService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(projectService, 'compareProject');
        comp.compareProject(entity, entity2);
        expect(projectService.compareProject).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
