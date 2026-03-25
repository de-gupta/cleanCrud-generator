package de.gupta.clean.crud.generator.code.generation.template.implementation.useCases.processing.infrastructure.configuration;

import de.gupta.clean.crud.generator.code.generation.template.api.domain.model.selection.TemplateGroup;
import de.gupta.clean.crud.generator.code.generation.template.api.domain.model.template.TemplateMetadata;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * Static registry that holds template metadata configuration for all available templates.
 * This class provides a centralized place to configure template metadata including
 * force overwrite flags, descriptions, dependencies, and other template-specific information.
 */
public final class TemplateMetadataRegistry
{
	private static final Map<String, TemplateMetadataConfig> TEMPLATE_METADATA_MAP;

	static
	{
		Map<String, TemplateMetadataConfig> templates = new HashMap<>();

		// DOMAIN Templates
		templates.put("BaseModel", new TemplateMetadataConfig(
				new TemplateMetadata(
						Optional.of("Base model interface template with validation and builder pattern"),
						Set.of("ModelBuilder", "Validatable"),
						Set.of("domain", "model", "base"),
						Optional.of("Clean CRUD Generator"),
						Optional.of("1.0"),
						false
				),
				TemplateGroup.DOMAIN
		));

		templates.put("DomainModel", new TemplateMetadataConfig(
				new TemplateMetadata(
						Optional.of("Domain-specific model interface extending base model"),
						Set.of("BaseDomainModel", "BaseModel"),
						Set.of("domain", "model", "specific"),
						Optional.of("Clean CRUD Generator"),
						Optional.of("1.0"),
						false
				),
				TemplateGroup.DOMAIN
		));

		templates.put("DomainAPITypeConverter", new TemplateMetadataConfig(
				new TemplateMetadata(
						Optional.of("Type converter between domain and API models"),
						Set.of("TypeConverter"),
						Set.of("domain", "api", "converter"),
						Optional.of("Clean CRUD Generator"),
						Optional.of("1.0"),
						false
				),
				TemplateGroup.DOMAIN
		));

		templates.put("DomainConstraintService", new TemplateMetadataConfig(
				new TemplateMetadata(
						Optional.of("Domain constraint validation service"),
						Set.of("ConstraintValidator"),
						Set.of("domain", "validation", "constraint"),
						Optional.of("Clean CRUD Generator"),
						Optional.of("1.0"),
						false
				),
				TemplateGroup.DOMAIN
		));

		templates.put("DomainModelBuilder", new TemplateMetadataConfig(
				new TemplateMetadata(
						Optional.of("Builder implementation for domain models"),
						Set.of("ModelBuilder"),
						Set.of("domain", "builder", "model"),
						Optional.of("Clean CRUD Generator"),
						Optional.of("1.0"),
						false
				),
				TemplateGroup.DOMAIN
		));

		templates.put("DomainModelBuilderFactory", new TemplateMetadataConfig(
				new TemplateMetadata(
						Optional.of("Factory for creating domain model builders"),
						Set.of("BuilderFactory"),
						Set.of("domain", "factory", "builder"),
						Optional.of("Clean CRUD Generator"),
						Optional.of("1.0"),
						false
				),
				TemplateGroup.DOMAIN
		));

		templates.put("DomainModelCreateDTO", new TemplateMetadataConfig(
				new TemplateMetadata(
						Optional.of("DTO for domain model creation"),
						Set.of("CreateDTO"),
						Set.of("domain", "dto", "create"),
						Optional.of("Clean CRUD Generator"),
						Optional.of("1.0"),
						false
				),
				TemplateGroup.DOMAIN
		));

		templates.put("DomainModelImpl", new TemplateMetadataConfig(
				new TemplateMetadata(
						Optional.of("Implementation class for domain models"),
						Set.of("DomainModel"),
						Set.of("domain", "model", "implementation"),
						Optional.of("Clean CRUD Generator"),
						Optional.of("1.0"),
						false
				),
				TemplateGroup.DOMAIN
		));

		templates.put("DomainModelPatcher", new TemplateMetadataConfig(
				new TemplateMetadata(
						Optional.of("Utility for patching domain models"),
						Set.of("ModelPatcher"),
						Set.of("domain", "patch", "utility"),
						Optional.of("Clean CRUD Generator"),
						Optional.of("1.0"),
						false
				),
				TemplateGroup.DOMAIN
		));

		templates.put("DomainModelResponseDTO", new TemplateMetadataConfig(
				new TemplateMetadata(
						Optional.of("DTO for domain model responses"),
						Set.of("ResponseDTO"),
						Set.of("domain", "dto", "response"),
						Optional.of("Clean CRUD Generator"),
						Optional.of("1.0"),
						false
				),
				TemplateGroup.DOMAIN
		));

		templates.put("DomainModelUpdatePatchDTO", new TemplateMetadataConfig(
				new TemplateMetadata(
						Optional.of("DTO for domain model patch updates"),
						Set.of("UpdateDTO", "PatchDTO"),
						Set.of("domain", "dto", "update", "patch"),
						Optional.of("Clean CRUD Generator"),
						Optional.of("1.0"),
						false
				),
				TemplateGroup.DOMAIN
		));

		templates.put("DomainResponseBuilder", new TemplateMetadataConfig(
				new TemplateMetadata(
						Optional.of("Builder for domain response objects"),
						Set.of("ResponseBuilder"),
						Set.of("domain", "response", "builder"),
						Optional.of("Clean CRUD Generator"),
						Optional.of("1.0"),
						false
				),
				TemplateGroup.DOMAIN
		));

		templates.put("DomainSecurityPolicy", new TemplateMetadataConfig(
				new TemplateMetadata(
						Optional.of("Security policy for domain operations"),
						Set.of("SecurityPolicy"),
						Set.of("domain", "security", "policy"),
						Optional.of("Clean CRUD Generator"),
						Optional.of("1.0"),
						false
				),
				TemplateGroup.DOMAIN
		));

		templates.put("LongDomainIDGenerator", new TemplateMetadataConfig(
				new TemplateMetadata(
						Optional.of("Long-based ID generator for domain entities"),
						Set.of("IDGenerator"),
						Set.of("domain", "id", "generator", "long"),
						Optional.of("Clean CRUD Generator"),
						Optional.of("1.0"),
						false
				),
				TemplateGroup.DOMAIN
		));

		// API Templates

		templates.put("APIModelCreateDTO", new TemplateMetadataConfig(
				new TemplateMetadata(
						Optional.of("API DTO for model creation"),
						Set.of("CreateDTO", "API"),
						Set.of("api", "dto", "create"),
						Optional.of("Clean CRUD Generator"),
						Optional.of("1.0"),
						false
				),
				TemplateGroup.API
		));

		templates.put("APIModelResponseDTO", new TemplateMetadataConfig(
				new TemplateMetadata(
						Optional.of("API DTO for model responses"),
						Set.of("ResponseDTO", "API"),
						Set.of("api", "dto", "response"),
						Optional.of("Clean CRUD Generator"),
						Optional.of("1.0"),
						false
				),
				TemplateGroup.API
		));

		templates.put("APIModelUpdatePatchDTO", new TemplateMetadataConfig(
				new TemplateMetadata(
						Optional.of("API DTO for model patch updates"),
						Set.of("UpdateDTO", "PatchDTO", "API"),
						Set.of("api", "dto", "update", "patch"),
						Optional.of("Clean CRUD Generator"),
						Optional.of("1.0"),
						false
				),
				TemplateGroup.API
		));

		templates.put("APIToDomainCreateAdapter", new TemplateMetadataConfig(
				new TemplateMetadata(
						Optional.of("Adapter from API create DTO to domain model"),
						Set.of("CreateAdapter"),
						Set.of("api", "adapter", "create"),
						Optional.of("Clean CRUD Generator"),
						Optional.of("1.0"),
						false
				),
				TemplateGroup.API
		));

		templates.put("APIToDomainUpdateAdapter", new TemplateMetadataConfig(
				new TemplateMetadata(
						Optional.of("Adapter from API update DTO to domain model"),
						Set.of("UpdateAdapter"),
						Set.of("api", "adapter", "update"),
						Optional.of("Clean CRUD Generator"),
						Optional.of("1.0"),
						false
				),
				TemplateGroup.API
		));

		templates.put("DomainToAPIResponseAdapter", new TemplateMetadataConfig(
				new TemplateMetadata(
						Optional.of("Adapter from domain model to API response DTO"),
						Set.of("ResponseAdapter"),
						Set.of("api", "adapter", "response"),
						Optional.of("Clean CRUD Generator"),
						Optional.of("1.0"),
						false
				),
				TemplateGroup.API
		));

		templates.put("SpringRestDeleteController", new TemplateMetadataConfig(
				new TemplateMetadata(
						Optional.of("Spring REST controller for delete operations"),
						Set.of("RestController", "Spring"),
						Set.of("api", "rest", "delete", "controller"),
						Optional.of("Clean CRUD Generator"),
						Optional.of("1.0"),
						false
				),
				TemplateGroup.API
		));

		templates.put("SpringRestFetchController", new TemplateMetadataConfig(
				new TemplateMetadata(
						Optional.of("Spring REST controller for fetch operations"),
						Set.of("RestController", "Spring"),
						Set.of("api", "rest", "fetch", "controller"),
						Optional.of("Clean CRUD Generator"),
						Optional.of("1.0"),
						false
				),
				TemplateGroup.API
		));

		templates.put("SpringRestSaveController", new TemplateMetadataConfig(
				new TemplateMetadata(
						Optional.of("Spring REST controller for save operations"),
						Set.of("RestController", "Spring"),
						Set.of("api", "rest", "save", "controller"),
						Optional.of("Clean CRUD Generator"),
						Optional.of("1.0"),
						false
				),
				TemplateGroup.API
		));

		templates.put("SpringRestUpdateController", new TemplateMetadataConfig(
				new TemplateMetadata(
						Optional.of("Spring REST controller for update operations"),
						Set.of("RestController", "Spring"),
						Set.of("api", "rest", "update", "controller"),
						Optional.of("Clean CRUD Generator"),
						Optional.of("1.0"),
						false
				),
				TemplateGroup.API
		));

		// INFRASTRUCTURE Templates
		templates.put("DomainPersistenceAdapterJpaRepository", new TemplateMetadataConfig(
				new TemplateMetadata(
						Optional.of("JPA repository adapter for domain persistence"),
						Set.of("JpaRepository", "PersistenceAdapter"),
						Set.of("infrastructure", "jpa", "repository", "adapter"),
						Optional.of("Clean CRUD Generator"),
						Optional.of("1.0"),
						false
				),
				TemplateGroup.INFRASTRUCTURE
		));

		templates.put("DomainPersistenceAdapterModel", new TemplateMetadataConfig(
				new TemplateMetadata(
						Optional.of("Persistence model adapter for domain entities"),
						Set.of("PersistenceAdapter", "Model"),
						Set.of("infrastructure", "persistence", "adapter", "model"),
						Optional.of("Clean CRUD Generator"),
						Optional.of("1.0"),
						false
				),
				TemplateGroup.INFRASTRUCTURE
		));

		templates.put("DomainPersistenceAdapterModelBuilderFactory", new TemplateMetadataConfig(
				new TemplateMetadata(
						Optional.of("Factory for persistence model adapter builders"),
						Set.of("BuilderFactory", "PersistenceAdapter"),
						Set.of("infrastructure", "persistence", "factory", "builder"),
						Optional.of("Clean CRUD Generator"),
						Optional.of("1.0"),
						false
				),
				TemplateGroup.INFRASTRUCTURE
		));

		templates.put("DomainPersistenceAdapterRepository", new TemplateMetadataConfig(
				new TemplateMetadata(
						Optional.of("Repository interface for domain persistence adapter"),
						Set.of("Repository", "PersistenceAdapter"),
						Set.of("infrastructure", "persistence", "repository"),
						Optional.of("Clean CRUD Generator"),
						Optional.of("1.0"),
						false
				),
				TemplateGroup.INFRASTRUCTURE
		));

		templates.put("DomainPersistenceIDAdapter", new TemplateMetadataConfig(
				new TemplateMetadata(
						Optional.of("ID adapter for domain persistence"),
						Set.of("IDAdapter", "Persistence"),
						Set.of("infrastructure", "persistence", "id", "adapter"),
						Optional.of("Clean CRUD Generator"),
						Optional.of("1.0"),
						false
				),
				TemplateGroup.INFRASTRUCTURE
		));

		templates.put("DomainPersistenceIDManagement", new TemplateMetadataConfig(
				new TemplateMetadata(
						Optional.of("ID management for domain persistence"),
						Set.of("IDManagement", "Persistence"),
						Set.of("infrastructure", "persistence", "id", "management"),
						Optional.of("Clean CRUD Generator"),
						Optional.of("1.0"),
						false
				),
				TemplateGroup.INFRASTRUCTURE
		));

		templates.put("DomainPersistenceModelAdapter", new TemplateMetadataConfig(
				new TemplateMetadata(
						Optional.of("Model adapter for domain persistence"),
						Set.of("ModelAdapter", "Persistence"),
						Set.of("infrastructure", "persistence", "model", "adapter"),
						Optional.of("Clean CRUD Generator"),
						Optional.of("1.0"),
						false
				),
				TemplateGroup.INFRASTRUCTURE
		));

		templates.put("DomainPersistenceTypeConverter", new TemplateMetadataConfig(
				new TemplateMetadata(
						Optional.of("Type converter for domain persistence"),
						Set.of("TypeConverter", "Persistence"),
						Set.of("infrastructure", "persistence", "converter"),
						Optional.of("Clean CRUD Generator"),
						Optional.of("1.0"),
						false
				),
				TemplateGroup.INFRASTRUCTURE
		));

		templates.put("JpaRepository", new TemplateMetadataConfig(
				new TemplateMetadata(
						Optional.of("JPA repository interface"),
						Set.of("JpaRepository", "Repository"),
						Set.of("infrastructure", "jpa", "repository"),
						Optional.of("Clean CRUD Generator"),
						Optional.of("1.0"),
						false
				),
				TemplateGroup.INFRASTRUCTURE
		));

		templates.put("CommonPersistenceConfiguration", new TemplateMetadataConfig(
				new TemplateMetadata(
						Optional.of("Shared persistence configuration for common infrastructure beans"),
						Set.of("Configuration", "Spring", "PersistenceTransactionRunner"),
						Set.of("common", "configuration", "persistence"),
						Optional.of("Clean CRUD Generator"),
						Optional.of("1.0"),
						false
				),
				TemplateGroup.COMMON
		));

		templates.put("ModuleConfiguration", new TemplateMetadataConfig(
				new TemplateMetadata(
						Optional.of("Spring module configuration"),
						Set.of("Configuration", "Spring"),
						Set.of("infrastructure", "configuration", "module"),
						Optional.of("Clean CRUD Generator"),
						Optional.of("1.0"),
						false
				),
				TemplateGroup.INFRASTRUCTURE
		));

		templates.put("PersistenceModel", new TemplateMetadataConfig(
				new TemplateMetadata(
						Optional.of("Persistence model entity"),
						Set.of("Entity", "PersistenceModel"),
						Set.of("infrastructure", "persistence", "model"),
						Optional.of("Clean CRUD Generator"),
						Optional.of("1.0"),
						false
				),
				TemplateGroup.INFRASTRUCTURE
		));

		templates.put("PersistenceModelBuilderFactory", new TemplateMetadataConfig(
				new TemplateMetadata(
						Optional.of("Factory for persistence model builders"),
						Set.of("BuilderFactory", "PersistenceModel"),
						Set.of("infrastructure", "persistence", "factory"),
						Optional.of("Clean CRUD Generator"),
						Optional.of("1.0"),
						false
				),
				TemplateGroup.INFRASTRUCTURE
		));

		templates.put("PersistenceModelImpl", new TemplateMetadataConfig(
				new TemplateMetadata(
						Optional.of("Implementation of persistence model"),
						Set.of("Entity", "Implementation"),
						Set.of("infrastructure", "persistence", "implementation"),
						Optional.of("Clean CRUD Generator"),
						Optional.of("1.0"),
						false
				),
				TemplateGroup.INFRASTRUCTURE
		));

		templates.put("PersistenceModelJpaDeleteRepository", new TemplateMetadataConfig(
				new TemplateMetadata(
						Optional.of("JPA delete repository for persistence model"),
						Set.of("JpaRepository", "Delete"),
						Set.of("infrastructure", "jpa", "delete", "repository"),
						Optional.of("Clean CRUD Generator"),
						Optional.of("1.0"),
						false
				),
				TemplateGroup.INFRASTRUCTURE
		));

		templates.put("PersistenceModelJpaFetchRepository", new TemplateMetadataConfig(
				new TemplateMetadata(
						Optional.of("JPA fetch repository for persistence model"),
						Set.of("JpaRepository", "Fetch"),
						Set.of("infrastructure", "jpa", "fetch", "repository"),
						Optional.of("Clean CRUD Generator"),
						Optional.of("1.0"),
						false
				),
				TemplateGroup.INFRASTRUCTURE
		));

		templates.put("PersistenceModelJpaSaveRepository", new TemplateMetadataConfig(
				new TemplateMetadata(
						Optional.of("JPA save repository for persistence model"),
						Set.of("JpaRepository", "Save"),
						Set.of("infrastructure", "jpa", "save", "repository"),
						Optional.of("Clean CRUD Generator"),
						Optional.of("1.0"),
						false
				),
				TemplateGroup.INFRASTRUCTURE
		));

		templates.put("DomainPersistenceAdapterHistoryModel", new TemplateMetadataConfig(
				new TemplateMetadata(
						Optional.of("History model for domain-persistence ID mappings"),
						Set.of("PersistenceAdapter", "History", "Model"),
						Set.of("infrastructure", "persistence", "adapter", "history", "model"),
						Optional.of("Clean CRUD Generator"),
						Optional.of("1.0"),
						false
				),
				TemplateGroup.INFRASTRUCTURE
		));

		templates.put("DomainPersistenceAdapterHistoryJpaRepository", new TemplateMetadataConfig(
				new TemplateMetadata(
						Optional.of("History JPA repository for domain-persistence ID mappings"),
						Set.of("JpaRepository", "PersistenceAdapter", "History"),
						Set.of("infrastructure", "persistence", "adapter", "history", "repository"),
						Optional.of("Clean CRUD Generator"),
						Optional.of("1.0"),
						false
				),
				TemplateGroup.INFRASTRUCTURE
		));

		templates.put("PersistenceModelHistory", new TemplateMetadataConfig(
				new TemplateMetadata(
						Optional.of("Tri-temporal history entity for persistence models"),
						Set.of("Entity", "History", "PersistenceModel"),
						Set.of("infrastructure", "persistence", "history", "entity"),
						Optional.of("Clean CRUD Generator"),
						Optional.of("1.0"),
						false
				),
				TemplateGroup.INFRASTRUCTURE
		));

		templates.put("PersistenceModelHistoryJpaRepository", new TemplateMetadataConfig(
				new TemplateMetadata(
						Optional.of("History JPA repository for persistence models"),
						Set.of("JpaRepository", "History", "PersistenceModel"),
						Set.of("infrastructure", "persistence", "history", "repository"),
						Optional.of("Clean CRUD Generator"),
						Optional.of("1.0"),
						false
				),
				TemplateGroup.INFRASTRUCTURE
		));

		templates.put("PersistenceHistorySnapshotFactory", new TemplateMetadataConfig(
				new TemplateMetadata(
						Optional.of("Snapshot factory for persistence-model history rows"),
						Set.of("History", "SnapshotFactory", "PersistenceModel"),
						Set.of("infrastructure", "persistence", "history", "factory"),
						Optional.of("Clean CRUD Generator"),
						Optional.of("1.0"),
						false
				),
				TemplateGroup.INFRASTRUCTURE
		));

		templates.put("PersistenceModelJpaUpdateRepository", new TemplateMetadataConfig(
				new TemplateMetadata(
						Optional.of("JPA update repository for persistence models"),
						Set.of("JpaRepository", "Update", "PersistenceModel"),
						Set.of("infrastructure", "jpa", "update", "repository"),
						Optional.of("Clean CRUD Generator"),
						Optional.of("1.0"),
						false
				),
				TemplateGroup.INFRASTRUCTURE
		));// USE_CASES Templates

		templates.put("ControllerSecurityAspect", new TemplateMetadataConfig(
				new TemplateMetadata(
						Optional.of("Security aspect for controllers"),
						Set.of("Security", "Aspect"),
						Set.of("usecase", "security", "aspect"),
						Optional.of("Clean CRUD Generator"),
						Optional.of("1.0"),
						false
				),
				TemplateGroup.COMMON
		));

		templates.put("DeleteApplicationController", new TemplateMetadataConfig(
				new TemplateMetadata(
						Optional.of("Application controller for delete operations"),
						Set.of("Controller", "Delete"),
						Set.of("usecase", "controller", "delete"),
						Optional.of("Clean CRUD Generator"),
						Optional.of("1.0"),
						false
				),
				TemplateGroup.USE_CASES
		));

		templates.put("DeletePersistenceService", new TemplateMetadataConfig(
				new TemplateMetadata(
						Optional.of("Persistence service for delete operations"),
						Set.of("PersistenceService", "Delete"),
						Set.of("usecase", "persistence", "delete"),
						Optional.of("Clean CRUD Generator"),
						Optional.of("1.0"),
						false
				),
				TemplateGroup.USE_CASES
		));

		templates.put("DeleteService", new TemplateMetadataConfig(
				new TemplateMetadata(
						Optional.of("Service for delete operations"),
						Set.of("Service", "Delete"),
						Set.of("usecase", "service", "delete"),
						Optional.of("Clean CRUD Generator"),
						Optional.of("1.0"),
						false
				),
				TemplateGroup.USE_CASES
		));

		templates.put("DeleteServiceFacade", new TemplateMetadataConfig(
				new TemplateMetadata(
						Optional.of("Facade for delete services"),
						Set.of("Facade", "Delete"),
						Set.of("usecase", "facade", "delete"),
						Optional.of("Clean CRUD Generator"),
						Optional.of("1.0"),
						false
				),
				TemplateGroup.USE_CASES
		));

		templates.put("DeletionPolicy", new TemplateMetadataConfig(
				new TemplateMetadata(
						Optional.of("Policy for deletion operations"),
						Set.of("Policy", "Deletion"),
						Set.of("usecase", "policy", "deletion"),
						Optional.of("Clean CRUD Generator"),
						Optional.of("1.0"),
						false
				),
				TemplateGroup.USE_CASES
		));

		templates.put("DuplicateInsertionMessage", new TemplateMetadataConfig(
				new TemplateMetadata(
						Optional.of("Message for duplicate insertion handling"),
						Set.of("Message", "DuplicateInsertion"),
						Set.of("usecase", "message", "duplicate"),
						Optional.of("Clean CRUD Generator"),
						Optional.of("1.0"),
						false
				),
				TemplateGroup.USE_CASES
		));

		templates.put("EqualityPolicy", new TemplateMetadataConfig(
				new TemplateMetadata(
						Optional.of("Explicit domain equality policy for duplicate detection"),
						Set.of("Policy", "Equality", "Domain"),
						Set.of("usecase", "policy", "equality"),
						Optional.of("Clean CRUD Generator"),
						Optional.of("1.0"),
						false
				),
				TemplateGroup.USE_CASES
		));

		templates.put("EndpointSecurityPolicy", new TemplateMetadataConfig(
				new TemplateMetadata(
						Optional.of("Security policy for endpoints"),
						Set.of("SecurityPolicy", "Endpoint"),
						Set.of("usecase", "security", "endpoint"),
						Optional.of("Clean CRUD Generator"),
						Optional.of("1.0"),
						false
				),
				TemplateGroup.USE_CASES
		));

		templates.put("FetchApplicationController", new TemplateMetadataConfig(
				new TemplateMetadata(
						Optional.of("Application controller for fetch operations"),
						Set.of("Controller", "Fetch"),
						Set.of("usecase", "controller", "fetch"),
						Optional.of("Clean CRUD Generator"),
						Optional.of("1.0"),
						false
				),
				TemplateGroup.USE_CASES
		));

		templates.put("FetchPersistenceService", new TemplateMetadataConfig(
				new TemplateMetadata(
						Optional.of("Persistence service for fetch operations"),
						Set.of("PersistenceService", "Fetch"),
						Set.of("usecase", "persistence", "fetch"),
						Optional.of("Clean CRUD Generator"),
						Optional.of("1.0"),
						false
				),
				TemplateGroup.USE_CASES
		));

		templates.put("FetchService", new TemplateMetadataConfig(
				new TemplateMetadata(
						Optional.of("Service for fetch operations"),
						Set.of("Service", "Fetch"),
						Set.of("usecase", "service", "fetch"),
						Optional.of("Clean CRUD Generator"),
						Optional.of("1.0"),
						false
				),
				TemplateGroup.USE_CASES
		));

		templates.put("FetchServiceFacade", new TemplateMetadataConfig(
				new TemplateMetadata(
						Optional.of("Facade for fetch services"),
						Set.of("Facade", "Fetch"),
						Set.of("usecase", "facade", "fetch"),
						Optional.of("Clean CRUD Generator"),
						Optional.of("1.0"),
						false
				),
				TemplateGroup.USE_CASES
		));

		templates.put("InsertionPolicy", new TemplateMetadataConfig(
				new TemplateMetadata(
						Optional.of("Policy for insertion operations"),
						Set.of("Policy", "Insertion"),
						Set.of("usecase", "policy", "insertion"),
						Optional.of("Clean CRUD Generator"),
						Optional.of("1.0"),
						false
				),
				TemplateGroup.USE_CASES
		));

		templates.put("ChangePolicy", new TemplateMetadataConfig(
				new TemplateMetadata(
						Optional.of("Policy for validating allowed changes during patch/update operations"),
						Set.of("Policy", "Change"),
						Set.of("usecase", "policy", "change"),
						Optional.of("Clean CRUD Generator"),
						Optional.of("1.0"),
						false
				),
				TemplateGroup.USE_CASES
		));

		templates.put("PatchPolicy", new TemplateMetadataConfig(
				new TemplateMetadata(
						Optional.of("Policy for patch operations"),
						Set.of("Policy", "Patch"),
						Set.of("usecase", "policy", "patch"),
						Optional.of("Clean CRUD Generator"),
						Optional.of("1.0"),
						false
				),
				TemplateGroup.USE_CASES
		));

		templates.put("SaveApplicationController", new TemplateMetadataConfig(
				new TemplateMetadata(
						Optional.of("Application controller for save operations"),
						Set.of("Controller", "Save"),
						Set.of("usecase", "controller", "save"),
						Optional.of("Clean CRUD Generator"),
						Optional.of("1.0"),
						false
				),
				TemplateGroup.USE_CASES
		));

		templates.put("SavePersistenceService", new TemplateMetadataConfig(
				new TemplateMetadata(
						Optional.of("Persistence service for save operations"),
						Set.of("PersistenceService", "Save"),
						Set.of("usecase", "persistence", "save"),
						Optional.of("Clean CRUD Generator"),
						Optional.of("1.0"),
						false
				),
				TemplateGroup.USE_CASES
		));

		templates.put("SaveService", new TemplateMetadataConfig(
				new TemplateMetadata(
						Optional.of("Service for save operations"),
						Set.of("Service", "Save"),
						Set.of("usecase", "service", "save"),
						Optional.of("Clean CRUD Generator"),
						Optional.of("1.0"),
						false
				),
				TemplateGroup.USE_CASES
		));

		templates.put("SaveServiceFacade", new TemplateMetadataConfig(
				new TemplateMetadata(
						Optional.of("Facade for save services"),
						Set.of("Facade", "Save"),
						Set.of("usecase", "facade", "save"),
						Optional.of("Clean CRUD Generator"),
						Optional.of("1.0"),
						false
				),
				TemplateGroup.USE_CASES
		));

		templates.put("UpdateApplicationController", new TemplateMetadataConfig(
				new TemplateMetadata(
						Optional.of("Application controller for update operations"),
						Set.of("Controller", "Update"),
						Set.of("usecase", "controller", "update"),
						Optional.of("Clean CRUD Generator"),
						Optional.of("1.0"),
						false
				),
				TemplateGroup.USE_CASES
		));

		templates.put("UpdatePersistenceService", new TemplateMetadataConfig(
				new TemplateMetadata(
						Optional.of("Persistence service for update operations"),
						Set.of("PersistenceService", "Update"),
						Set.of("usecase", "persistence", "update"),
						Optional.of("Clean CRUD Generator"),
						Optional.of("1.0"),
						false
				),
				TemplateGroup.USE_CASES
		));

		templates.put("UpdateService", new TemplateMetadataConfig(
				new TemplateMetadata(
						Optional.of("Service for update operations"),
						Set.of("Service", "Update"),
						Set.of("usecase", "service", "update"),
						Optional.of("Clean CRUD Generator"),
						Optional.of("1.0"),
						false
				),
				TemplateGroup.USE_CASES
		));

		templates.put("UpdateServiceFacade", new TemplateMetadataConfig(
				new TemplateMetadata(
						Optional.of("Facade for update services"),
						Set.of("Facade", "Update"),
						Set.of("usecase", "facade", "update"),
						Optional.of("Clean CRUD Generator"),
						Optional.of("1.0"),
						false
				),
				TemplateGroup.USE_CASES
		));

		TEMPLATE_METADATA_MAP = Map.copyOf(templates);
	}

	public static Optional<TemplateMetadataConfig> getTemplateMetadata(String templateName)
	{
		return Optional.ofNullable(TEMPLATE_METADATA_MAP.get(templateName));
	}

	public static Map<String, TemplateMetadataConfig> getAllTemplateMetadata()
	{
		return Map.copyOf(TEMPLATE_METADATA_MAP);
	}

	public static boolean hasTemplate(String templateName)
	{
		return TEMPLATE_METADATA_MAP.containsKey(templateName);
	}

	private TemplateMetadataRegistry()
	{
	}

	public record TemplateMetadataConfig(TemplateMetadata metadata, TemplateGroup templateGroup)
	{
	}
}
