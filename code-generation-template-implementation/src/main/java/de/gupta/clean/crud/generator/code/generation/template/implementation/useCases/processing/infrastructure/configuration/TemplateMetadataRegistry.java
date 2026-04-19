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
				TemplateGroup.DOMAIN_MODELS
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
				TemplateGroup.DOMAIN_MODELS
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
				TemplateGroup.DOMAIN_SUPPORT
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
				TemplateGroup.DOMAIN_SUPPORT
		));

		templates.put("ExistingModelsConstraintService", new TemplateMetadataConfig(
				new TemplateMetadata(
						Optional.of("Constraint service for additional checks against the existing model set"),
						Set.of("ConstraintValidator", "ExistingModels"),
						Set.of("domain", "validation", "constraint", "existing"),
						Optional.of("Clean CRUD Generator"),
						Optional.of("1.0"),
						false
				),
				TemplateGroup.DOMAIN_SUPPORT
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
				TemplateGroup.DOMAIN_MODELS
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
				TemplateGroup.DOMAIN_MODELS
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
				TemplateGroup.DOMAIN_MODELS
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
				TemplateGroup.DOMAIN_MODELS
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
				TemplateGroup.DOMAIN_MODELS
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
				TemplateGroup.DOMAIN_MODELS
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
				TemplateGroup.DOMAIN_MODELS
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
				TemplateGroup.DOMAIN_MODELS
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
				TemplateGroup.SECURITY
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
				TemplateGroup.DOMAIN_SUPPORT
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
				TemplateGroup.API_DTOS
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
				TemplateGroup.API_DTOS
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
				TemplateGroup.API_DTOS
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
				TemplateGroup.API_ADAPTERS
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
				TemplateGroup.API_ADAPTERS
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
				TemplateGroup.API_ADAPTERS
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
				TemplateGroup.API_CONTROLLERS
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
				TemplateGroup.API_CONTROLLERS
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
				TemplateGroup.API_CONTROLLERS
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
				TemplateGroup.API_CONTROLLERS
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
				TemplateGroup.PERSISTENCE_REPOSITORIES
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
				TemplateGroup.PERSISTENCE_MODELS
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
				TemplateGroup.PERSISTENCE_MODELS
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
				TemplateGroup.PERSISTENCE_REPOSITORIES
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
				TemplateGroup.PERSISTENCE_ADAPTERS
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
				TemplateGroup.PERSISTENCE_ADAPTERS
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
				TemplateGroup.PERSISTENCE_ADAPTERS
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
				TemplateGroup.PERSISTENCE_ADAPTERS
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
				TemplateGroup.PERSISTENCE_REPOSITORIES
		));

		templates.put("CommonPersistenceConfiguration", new TemplateMetadataConfig(
				new TemplateMetadata(
						Optional.of("Shared persistence configuration for common infrastructure beans"),
						Set.of("Configuration", "Spring", "PersistenceTransactionRunner", "AggregateLifecycleEngine"),
						Set.of("common", "configuration", "persistence"),
						Optional.of("Clean CRUD Generator"),
						Optional.of("1.0"),
						false
				),
				TemplateGroup.COMMON
		));

		templates.put("IdentityAPIDomainIDAdapter", new TemplateMetadataConfig(
				new TemplateMetadata(
						Optional.of("Shared identity adapter between API and domain IDs"),
						Set.of("Identity", "API", "Domain", "ID", "Adapter"),
						Set.of("common", "adapter", "id"),
						Optional.of("Clean CRUD Generator"),
						Optional.of("1.0"),
						false
				),
				TemplateGroup.COMMON
		));

		templates.put("GlobalSpringRestControllerAdvice", new TemplateMetadataConfig(
				new TemplateMetadata(
						Optional.of("Shared global Spring REST controller advice"),
						Set.of("ControllerAdvice", "Spring", "Web"),
						Set.of("common", "configuration", "web", "advice"),
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
				TemplateGroup.CONFIGURATION
		));

		templates.put("CrudPortsConfiguration", new TemplateMetadataConfig(
				new TemplateMetadata(
						Optional.of("Aggregate CRUD ports configuration"),
						Set.of("Configuration", "Aggregate", "Ports", "CRUD"),
						Set.of("configuration", "crud", "aggregate", "ports"),
						Optional.of("Clean CRUD Generator"),
						Optional.of("1.0"),
						false
				),
				TemplateGroup.CONFIGURATION
		));

		templates.put("CrudDefinitionConfiguration", new TemplateMetadataConfig(
				new TemplateMetadata(
						Optional.of("Aggregate CRUD definition configuration"),
						Set.of("Configuration", "Aggregate", "Definition", "CRUD"),
						Set.of("configuration", "crud", "aggregate", "definition"),
						Optional.of("Clean CRUD Generator"),
						Optional.of("1.0"),
						false
				),
				TemplateGroup.CONFIGURATION
		));

		templates.put("CrudServicesConfiguration", new TemplateMetadataConfig(
				new TemplateMetadata(
						Optional.of("Aggregate CRUD services configuration"),
						Set.of("Configuration", "Aggregate", "Services", "CRUD"),
						Set.of("configuration", "crud", "aggregate", "services"),
						Optional.of("Clean CRUD Generator"),
						Optional.of("1.0"),
						false
				),
				TemplateGroup.CONFIGURATION
		));

		templates.put("CrudRelationshipConfiguration", new TemplateMetadataConfig(
				new TemplateMetadata(
						Optional.of("Aggregate CRUD relationship configuration"),
						Set.of("Configuration", "Aggregate", "Relationships", "CRUD"),
						Set.of("configuration", "crud", "aggregate", "relationship"),
						Optional.of("Clean CRUD Generator"),
						Optional.of("1.0"),
						false
				),
				TemplateGroup.CONFIGURATION
		));

		templates.put("CrudRelationshipConfiguration", new TemplateMetadataConfig(
				new TemplateMetadata(
						Optional.of("Aggregate CRUD relationship configuration"),
						Set.of("Configuration", "Aggregate", "Relationships", "CRUD"),
						Set.of("configuration", "crud", "aggregate", "relationship"),
						Optional.of("Clean CRUD Generator"),
						Optional.of("1.0"),
						false
				),
				TemplateGroup.CONFIGURATION
		));

		templates.put("ExistingDomainModelsSupplier", new TemplateMetadataConfig(
				new TemplateMetadata(
						Optional.of("Supplier for loading existing domain models from persistence"),
						Set.of("Supplier", "Persistence", "ExistingModels"),
						Set.of("infrastructure", "persistence", "service", "existing-models"),
						Optional.of("Clean CRUD Generator"),
						Optional.of("1.0"),
						false
				),
				TemplateGroup.PERSISTENCE_ADAPTERS
		));

		templates.put("AuditActorSupplier", new TemplateMetadataConfig(
				new TemplateMetadata(
						Optional.of("Per-module audit actor supplier for historized persistence operations"),
						Set.of("Audit", "Actor", "Supplier", "Persistence"),
						Set.of("infrastructure", "persistence", "audit", "supplier"),
						Optional.of("Clean CRUD Generator"),
						Optional.of("1.0"),
						false
				),
				TemplateGroup.PERSISTENCE_HISTORY
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
				TemplateGroup.PERSISTENCE_MODELS
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
				TemplateGroup.PERSISTENCE_MODELS
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
				TemplateGroup.PERSISTENCE_MODELS
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
				TemplateGroup.PERSISTENCE_REPOSITORIES
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
				TemplateGroup.PERSISTENCE_REPOSITORIES
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
				TemplateGroup.PERSISTENCE_REPOSITORIES
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
				TemplateGroup.PERSISTENCE_HISTORY
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
				TemplateGroup.PERSISTENCE_HISTORY
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
				TemplateGroup.PERSISTENCE_HISTORY
		));

		templates.put("PersistenceJpaConverters", new TemplateMetadataConfig(
				new TemplateMetadata(
						Optional.of("JPA converter stubs for complex persistence fields"),
						Set.of("JPA", "AttributeConverter", "Persistence", "Converter"),
						Set.of("infrastructure", "persistence", "converter", "jpa"),
						Optional.of("Clean CRUD Generator"),
						Optional.of("1.0"),
						false
				),
				TemplateGroup.PERSISTENCE_HISTORY
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
				TemplateGroup.PERSISTENCE_HISTORY
		));

		templates.put("PersistenceModelHistoryRepository", new TemplateMetadataConfig(
				new TemplateMetadata(
						Optional.of("History repository adapter for persistence models"),
						Set.of("History", "Repository", "Adapter", "PersistenceModel"),
						Set.of("infrastructure", "persistence", "history", "adapter", "repository"),
						Optional.of("Clean CRUD Generator"),
						Optional.of("1.0"),
						false
				),
				TemplateGroup.PERSISTENCE_HISTORY
		));

		templates.put("DomainPersistenceAdapterHistoryRepository", new TemplateMetadataConfig(
				new TemplateMetadata(
						Optional.of("History repository adapter for domain-persistence ID mappings"),
						Set.of("History", "Repository", "Adapter", "PersistenceAdapter"),
						Set.of("infrastructure", "persistence", "adapter", "history", "repository"),
						Optional.of("Clean CRUD Generator"),
						Optional.of("1.0"),
						false
				),
				TemplateGroup.PERSISTENCE_HISTORY
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
				TemplateGroup.PERSISTENCE_HISTORY
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
				TemplateGroup.PERSISTENCE_REPOSITORIES
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
				TemplateGroup.SECURITY
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
				TemplateGroup.USE_CASE_DELETE
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
				TemplateGroup.USE_CASE_DELETE
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
				TemplateGroup.USE_CASE_DELETE
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
				TemplateGroup.USE_CASE_DELETE
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
				TemplateGroup.USE_CASE_SAVE
		));

		templates.put("DuplicateDefinition", new TemplateMetadataConfig(
				new TemplateMetadata(
						Optional.of("Explicit duplicate-definition contract for duplicate detection"),
						Set.of("Duplicate", "Definition", "Domain"),
						Set.of("usecase", "duplicate", "definition"),
						Optional.of("Clean CRUD Generator"),
						Optional.of("1.0"),
						false
				),
				TemplateGroup.USE_CASE_SAVE
		));

		templates.put("DuplicateKey", new TemplateMetadataConfig(
				new TemplateMetadata(
						Optional.of("Typed duplicate key record for generated duplicate-definition scaffolding"),
						Set.of("Duplicate", "Key", "Record"),
						Set.of("usecase", "duplicate", "key"),
						Optional.of("Clean CRUD Generator"),
						Optional.of("1.0"),
						false
				),
				TemplateGroup.USE_CASE_SAVE
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
				TemplateGroup.SECURITY
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
				TemplateGroup.USE_CASE_FETCH
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
				TemplateGroup.USE_CASE_FETCH
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
				TemplateGroup.USE_CASE_FETCH
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
				TemplateGroup.USE_CASE_SAVE
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
				TemplateGroup.USE_CASE_UPDATE
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
				TemplateGroup.USE_CASE_UPDATE
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
				TemplateGroup.USE_CASE_SAVE
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
				TemplateGroup.USE_CASE_SAVE
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
				TemplateGroup.USE_CASE_SAVE
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
				TemplateGroup.USE_CASE_UPDATE
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
				TemplateGroup.USE_CASE_UPDATE
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
				TemplateGroup.USE_CASE_UPDATE
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
