package de.gupta.clean.crud.generator.code.generation.template.implementation.useCases.processing.infrastructure.configuration;

import de.gupta.clean.crud.generator.code.generation.template.api.domain.model.selection.TemplateGroup;
import de.gupta.clean.crud.generator.code.generation.template.api.domain.model.template.TemplateMetadata;

import java.util.LinkedHashMap;
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
	private static final Map<String, TemplateMetadataConfig> TEMPLATE_METADATA_MAP = createTemplates();

	private static Map<String, TemplateMetadataConfig> createTemplates()
	{
		var templates = new LinkedHashMap<String, TemplateMetadataConfig>();
		registerDomainTemplates(templates);
		registerApiTemplates(templates);
		registerPersistenceTemplates(templates);
		registerCommonTemplates(templates);
		registerConfigurationTemplates(templates);
		registerSecurityTemplates(templates);
		registerUseCaseTemplates(templates);
		return Map.copyOf(templates);
	}

	private static void registerDomainTemplates(final Map<String, TemplateMetadataConfig> templates)
	{
		register(templates, "BaseModel", "Base model interface template with validation and builder pattern",
				Set.of("ModelBuilder", "Validatable"), Set.of("domain", "model", "base"),
				TemplateGroup.DOMAIN_MODELS);
		register(templates, "DomainModel", "Domain-specific model interface extending base model",
				Set.of("BaseDomainModel", "BaseModel"), Set.of("domain", "model", "specific"),
				TemplateGroup.DOMAIN_MODELS);
		register(templates, "DomainAPITypeConverter", "Type converter between domain and API models",
				Set.of("TypeConverter"), Set.of("domain", "api", "converter"), TemplateGroup.DOMAIN_SUPPORT);
		register(templates, "DomainConstraintService", "Domain constraint validation service",
				Set.of("ConstraintValidator"), Set.of("domain", "validation", "constraint"),
				TemplateGroup.DOMAIN_SUPPORT);
		register(templates, "ExistingModelsConstraintService",
				"Constraint service for additional checks against the existing model set",
				Set.of("ConstraintValidator", "ExistingModels"),
				Set.of("domain", "validation", "constraint", "existing"), TemplateGroup.DOMAIN_SUPPORT);
		register(templates, "DomainModelBuilder", "Builder implementation for domain models",
				Set.of("ModelBuilder"), Set.of("domain", "builder", "model"), TemplateGroup.DOMAIN_MODELS);
		register(templates, "DomainModelBuilderFactory", "Factory for creating domain model builders",
				Set.of("BuilderFactory"), Set.of("domain", "factory", "builder"), TemplateGroup.DOMAIN_MODELS);
		register(templates, "DomainModelCreateDTO", "DTO for domain model creation", Set.of("CreateDTO"),
				Set.of("domain", "dto", "create"), TemplateGroup.DOMAIN_MODELS);
		register(templates, "DomainModelImpl", "Implementation class for domain models", Set.of("DomainModel"),
				Set.of("domain", "model", "implementation"), TemplateGroup.DOMAIN_MODELS);
		register(templates, "DomainModelPatcher", "Utility for patching domain models", Set.of("ModelPatcher"),
				Set.of("domain", "patch", "utility"), TemplateGroup.DOMAIN_MODELS);
		register(templates, "DomainModelResponseDTO", "DTO for domain model responses", Set.of("ResponseDTO"),
				Set.of("domain", "dto", "response"), TemplateGroup.DOMAIN_MODELS);
		register(templates, "DomainModelUpdatePatchDTO", "DTO for domain model patch updates",
				Set.of("UpdateDTO", "PatchDTO"), Set.of("domain", "dto", "update", "patch"),
				TemplateGroup.DOMAIN_MODELS);
		register(templates, "DomainResponseBuilder", "Builder for domain response objects",
				Set.of("ResponseBuilder"), Set.of("domain", "response", "builder"), TemplateGroup.DOMAIN_MODELS);
		register(templates, "LongDomainIDGenerator", "Long-based ID generator for domain entities",
				Set.of("IDGenerator"), Set.of("domain", "id", "generator", "long"),
				TemplateGroup.DOMAIN_SUPPORT);
	}

	private static void registerApiTemplates(final Map<String, TemplateMetadataConfig> templates)
	{
		register(templates, "APIModelCreateDTO", "API DTO for model creation", Set.of("CreateDTO", "API"),
				Set.of("api", "dto", "create"), TemplateGroup.API_DTOS);
		register(templates, "APIModelResponseDTO", "API DTO for model responses", Set.of("ResponseDTO", "API"),
				Set.of("api", "dto", "response"), TemplateGroup.API_DTOS);
		register(templates, "APIModelUpdatePatchDTO", "API DTO for model patch updates",
				Set.of("UpdateDTO", "PatchDTO", "API"), Set.of("api", "dto", "update", "patch"),
				TemplateGroup.API_DTOS);
		register(templates, "APIToDomainCreateAdapter", "Adapter from API create DTO to domain model",
				Set.of("CreateAdapter"), Set.of("api", "adapter", "create"), TemplateGroup.API_ADAPTERS);
		register(templates, "APIToDomainUpdateAdapter", "Adapter from API update DTO to domain model",
				Set.of("UpdateAdapter"), Set.of("api", "adapter", "update"), TemplateGroup.API_ADAPTERS);
		register(templates, "DomainToAPIResponseAdapter", "Adapter from domain model to API response DTO",
				Set.of("ResponseAdapter"), Set.of("api", "adapter", "response"), TemplateGroup.API_ADAPTERS);
		register(templates, "SpringRestDeleteController", "Spring REST controller for delete operations",
				Set.of("RestController", "Spring"), Set.of("api", "rest", "delete", "controller"),
				TemplateGroup.API_CONTROLLERS);
		register(templates, "SpringRestFetchController", "Spring REST controller for fetch operations",
				Set.of("RestController", "Spring"), Set.of("api", "rest", "fetch", "controller"),
				TemplateGroup.API_CONTROLLERS);
		register(templates, "SpringRestSaveController", "Spring REST controller for save operations",
				Set.of("RestController", "Spring"), Set.of("api", "rest", "save", "controller"),
				TemplateGroup.API_CONTROLLERS);
		register(templates, "SpringRestUpdateController", "Spring REST controller for update operations",
				Set.of("RestController", "Spring"), Set.of("api", "rest", "update", "controller"),
				TemplateGroup.API_CONTROLLERS);
	}

	private static void registerPersistenceTemplates(final Map<String, TemplateMetadataConfig> templates)
	{
		register(templates, "DomainPersistenceAdapterJpaRepository",
				"JPA repository adapter for domain persistence",
				Set.of("JpaRepository", "PersistenceAdapter"),
				Set.of("infrastructure", "jpa", "repository", "adapter"),
				TemplateGroup.PERSISTENCE_REPOSITORIES);
		register(templates, "DomainPersistenceAdapterModel", "Persistence model adapter for domain entities",
				Set.of("PersistenceAdapter", "Model"),
				Set.of("infrastructure", "persistence", "adapter", "model"),
				TemplateGroup.PERSISTENCE_MODELS);
		register(templates, "DomainPersistenceAdapterModelBuilderFactory",
				"Factory for persistence model adapter builders",
				Set.of("BuilderFactory", "PersistenceAdapter"),
				Set.of("infrastructure", "persistence", "factory", "builder"),
				TemplateGroup.PERSISTENCE_MODELS);
		register(templates, "DomainPersistenceAdapterRepository",
				"Repository interface for domain persistence adapter",
				Set.of("Repository", "PersistenceAdapter"),
				Set.of("infrastructure", "persistence", "repository"),
				TemplateGroup.PERSISTENCE_REPOSITORIES);
		register(templates, "DomainPersistenceIDAdapter", "ID adapter for domain persistence",
				Set.of("IDAdapter", "Persistence"),
				Set.of("infrastructure", "persistence", "id", "adapter"),
				TemplateGroup.PERSISTENCE_ADAPTERS);
		register(templates, "DomainPersistenceIDManagement", "ID management for domain persistence",
				Set.of("IDManagement", "Persistence"),
				Set.of("infrastructure", "persistence", "id", "management"),
				TemplateGroup.PERSISTENCE_ADAPTERS);
		register(templates, "DomainPersistenceModelAdapter", "Model adapter for domain persistence",
				Set.of("ModelAdapter", "Persistence"),
				Set.of("infrastructure", "persistence", "model", "adapter"),
				TemplateGroup.PERSISTENCE_ADAPTERS);
		register(templates, "DomainPersistenceTypeConverter", "Type converter for domain persistence",
				Set.of("TypeConverter", "Persistence"),
				Set.of("infrastructure", "persistence", "converter"),
				TemplateGroup.PERSISTENCE_ADAPTERS);
		register(templates, "JpaRepository", "JPA repository interface",
				Set.of("JpaRepository", "Repository"), Set.of("infrastructure", "jpa", "repository"),
				TemplateGroup.PERSISTENCE_REPOSITORIES);
		register(templates, "ExistingDomainModelsSupplier",
				"Supplier for loading existing domain models from persistence",
				Set.of("Supplier", "Persistence", "ExistingModels"),
				Set.of("infrastructure", "persistence", "service", "existing-models"),
				TemplateGroup.PERSISTENCE_ADAPTERS);
		register(templates, "AuditActorSupplier",
				"Per-module audit actor supplier for historized persistence operations",
				Set.of("Audit", "Actor", "Supplier", "Persistence"),
				Set.of("infrastructure", "persistence", "audit", "supplier"),
				TemplateGroup.PERSISTENCE_HISTORY);
		register(templates, "PersistenceModel", "Persistence model entity",
				Set.of("Entity", "PersistenceModel"),
				Set.of("infrastructure", "persistence", "model"),
				TemplateGroup.PERSISTENCE_MODELS);
		register(templates, "PersistenceModelBuilderFactory", "Factory for persistence model builders",
				Set.of("BuilderFactory", "PersistenceModel"),
				Set.of("infrastructure", "persistence", "factory"),
				TemplateGroup.PERSISTENCE_MODELS);
		register(templates, "PersistenceModelImpl", "Implementation of persistence model",
				Set.of("Entity", "Implementation"),
				Set.of("infrastructure", "persistence", "implementation"),
				TemplateGroup.PERSISTENCE_MODELS);
		register(templates, "PersistenceModelJpaDeleteRepository",
				"JPA delete repository for persistence model",
				Set.of("JpaRepository", "Delete"),
				Set.of("infrastructure", "jpa", "delete", "repository"),
				TemplateGroup.PERSISTENCE_REPOSITORIES);
		register(templates, "PersistenceModelJpaFetchRepository",
				"JPA fetch repository for persistence model",
				Set.of("JpaRepository", "Fetch"),
				Set.of("infrastructure", "jpa", "fetch", "repository"),
				TemplateGroup.PERSISTENCE_REPOSITORIES);
		register(templates, "PersistenceModelJpaSaveRepository",
				"JPA save repository for persistence model",
				Set.of("JpaRepository", "Save"),
				Set.of("infrastructure", "jpa", "save", "repository"),
				TemplateGroup.PERSISTENCE_REPOSITORIES);
		register(templates, "DomainPersistenceAdapterHistoryModel",
				"History model for domain-persistence ID mappings",
				Set.of("PersistenceAdapter", "History", "Model"),
				Set.of("infrastructure", "persistence", "adapter", "history", "model"),
				TemplateGroup.PERSISTENCE_HISTORY);
		register(templates, "DomainPersistenceAdapterHistoryJpaRepository",
				"History JPA repository for domain-persistence ID mappings",
				Set.of("JpaRepository", "PersistenceAdapter", "History"),
				Set.of("infrastructure", "persistence", "adapter", "history", "repository"),
				TemplateGroup.PERSISTENCE_HISTORY);
		register(templates, "PersistenceModelHistory", "Tri-temporal history entity for persistence models",
				Set.of("Entity", "History", "PersistenceModel"),
				Set.of("infrastructure", "persistence", "history", "entity"),
				TemplateGroup.PERSISTENCE_HISTORY);
		register(templates, "PersistenceJpaConverters",
				"JPA converter stubs for complex persistence fields",
				Set.of("JPA", "AttributeConverter", "Persistence", "Converter"),
				Set.of("infrastructure", "persistence", "converter", "jpa"),
				TemplateGroup.PERSISTENCE_HISTORY);
		register(templates, "PersistenceModelHistoryJpaRepository",
				"History JPA repository for persistence models",
				Set.of("JpaRepository", "History", "PersistenceModel"),
				Set.of("infrastructure", "persistence", "history", "repository"),
				TemplateGroup.PERSISTENCE_HISTORY);
		register(templates, "PersistenceModelHistoryRepository",
				"History repository adapter for persistence models",
				Set.of("History", "Repository", "Adapter", "PersistenceModel"),
				Set.of("infrastructure", "persistence", "history", "adapter", "repository"),
				TemplateGroup.PERSISTENCE_HISTORY);
		register(templates, "DomainPersistenceAdapterHistoryRepository",
				"History repository adapter for domain-persistence ID mappings",
				Set.of("History", "Repository", "Adapter", "PersistenceAdapter"),
				Set.of("infrastructure", "persistence", "adapter", "history", "repository"),
				TemplateGroup.PERSISTENCE_HISTORY);
		register(templates, "PersistenceHistorySnapshotFactory",
				"Snapshot factory for persistence-model history rows",
				Set.of("History", "SnapshotFactory", "PersistenceModel"),
				Set.of("infrastructure", "persistence", "history", "factory"),
				TemplateGroup.PERSISTENCE_HISTORY);
		register(templates, "PersistenceModelJpaUpdateRepository",
				"JPA update repository for persistence models",
				Set.of("JpaRepository", "Update", "PersistenceModel"),
				Set.of("infrastructure", "jpa", "update", "repository"),
				TemplateGroup.PERSISTENCE_REPOSITORIES);
	}

	private static void registerCommonTemplates(final Map<String, TemplateMetadataConfig> templates)
	{
		register(templates, "CommonPersistenceConfiguration",
				"Shared persistence configuration for common infrastructure beans",
				Set.of("Configuration", "Spring", "PersistenceTransactionRunner", "AggregateLifecycleEngine"),
				Set.of("common", "configuration", "persistence"),
				TemplateGroup.COMMON);
		register(templates, "IdentityAPIDomainIDAdapter",
				"Shared identity adapter between API and domain IDs",
				Set.of("Identity", "API", "Domain", "ID", "Adapter"),
				Set.of("common", "adapter", "id"),
				TemplateGroup.COMMON);
		register(templates, "GlobalSpringRestControllerAdvice",
				"Shared global Spring REST controller advice",
				Set.of("ControllerAdvice", "Spring", "Web"),
				Set.of("common", "configuration", "web", "advice"),
				TemplateGroup.COMMON);
	}

	private static void registerConfigurationTemplates(final Map<String, TemplateMetadataConfig> templates)
	{
		register(templates, "ModuleConfiguration", "Spring module configuration",
				Set.of("Configuration", "Spring"),
				Set.of("infrastructure", "configuration", "module"),
				TemplateGroup.CONFIGURATION);
		register(templates, "CrudPortsConfiguration", "Aggregate CRUD ports configuration",
				Set.of("Configuration", "Aggregate", "Ports", "CRUD"),
				Set.of("configuration", "crud", "aggregate", "ports"),
				TemplateGroup.CONFIGURATION);
		register(templates, "CrudDefinitionConfiguration", "Aggregate CRUD definition configuration",
				Set.of("Configuration", "Aggregate", "Definition", "CRUD"),
				Set.of("configuration", "crud", "aggregate", "definition"),
				TemplateGroup.CONFIGURATION);
		register(templates, "CrudServicesConfiguration", "Aggregate CRUD services configuration",
				Set.of("Configuration", "Aggregate", "Services", "CRUD"),
				Set.of("configuration", "crud", "aggregate", "services"),
				TemplateGroup.CONFIGURATION);
		register(templates, "CrudRelationshipConfiguration", "Aggregate CRUD relationship configuration",
				Set.of("Configuration", "Aggregate", "Relationships", "CRUD"),
				Set.of("configuration", "crud", "aggregate", "relationship"),
				TemplateGroup.CONFIGURATION);
	}

	private static void registerSecurityTemplates(final Map<String, TemplateMetadataConfig> templates)
	{
		register(templates, "DomainSecurityPolicy", "Security policy for domain operations",
				Set.of("SecurityPolicy"), Set.of("domain", "security", "policy"), TemplateGroup.SECURITY);
		register(templates, "ControllerSecurityAspect", "Security aspect for controllers",
				Set.of("Security", "Aspect"), Set.of("usecase", "security", "aspect"), TemplateGroup.SECURITY);
		register(templates, "EndpointSecurityPolicy", "Security policy for endpoints",
				Set.of("SecurityPolicy", "Endpoint"), Set.of("usecase", "security", "endpoint"),
				TemplateGroup.SECURITY);
	}

	private static void registerUseCaseTemplates(final Map<String, TemplateMetadataConfig> templates)
	{
		register(templates, "DeleteApplicationController", "Application controller for delete operations",
				Set.of("Controller", "Delete"), Set.of("usecase", "controller", "delete"),
				TemplateGroup.USE_CASE_DELETE);
		register(templates, "DeletePersistenceService", "Persistence service for delete operations",
				Set.of("PersistenceService", "Delete"), Set.of("usecase", "persistence", "delete"),
				TemplateGroup.USE_CASE_DELETE);
		register(templates, "DeleteServiceFacade", "Facade for delete services",
				Set.of("Facade", "Delete"), Set.of("usecase", "facade", "delete"),
				TemplateGroup.USE_CASE_DELETE);
		register(templates, "DeletionPolicy", "Policy for deletion operations",
				Set.of("Policy", "Deletion"), Set.of("usecase", "policy", "deletion"),
				TemplateGroup.USE_CASE_DELETE);
		register(templates, "DuplicateInsertionMessage", "Message for duplicate insertion handling",
				Set.of("Message", "DuplicateInsertion"), Set.of("usecase", "message", "duplicate"),
				TemplateGroup.USE_CASE_SAVE);
		register(templates, "DuplicateDefinition",
				"Explicit duplicate-definition contract for duplicate detection",
				Set.of("Duplicate", "Definition", "Domain"),
				Set.of("usecase", "duplicate", "definition"),
				TemplateGroup.USE_CASE_SAVE);
		register(templates, "DuplicateKey",
				"Typed duplicate key record for generated duplicate-definition scaffolding",
				Set.of("Duplicate", "Key", "Record"), Set.of("usecase", "duplicate", "key"),
				TemplateGroup.USE_CASE_SAVE);
		register(templates, "FetchApplicationController", "Application controller for fetch operations",
				Set.of("Controller", "Fetch"), Set.of("usecase", "controller", "fetch"),
				TemplateGroup.USE_CASE_FETCH);
		register(templates, "FetchPersistenceService", "Persistence service for fetch operations",
				Set.of("PersistenceService", "Fetch"), Set.of("usecase", "persistence", "fetch"),
				TemplateGroup.USE_CASE_FETCH);
		register(templates, "FetchServiceFacade", "Facade for fetch services",
				Set.of("Facade", "Fetch"), Set.of("usecase", "facade", "fetch"),
				TemplateGroup.USE_CASE_FETCH);
		register(templates, "InsertionPolicy", "Policy for insertion operations",
				Set.of("Policy", "Insertion"), Set.of("usecase", "policy", "insertion"),
				TemplateGroup.USE_CASE_SAVE);
		register(templates, "ChangePolicy",
				"Policy for validating allowed changes during patch/update operations",
				Set.of("Policy", "Change"), Set.of("usecase", "policy", "change"),
				TemplateGroup.USE_CASE_UPDATE);
		register(templates, "PatchPolicy", "Policy for patch operations",
				Set.of("Policy", "Patch"), Set.of("usecase", "policy", "patch"),
				TemplateGroup.USE_CASE_UPDATE);
		register(templates, "SaveApplicationController", "Application controller for save operations",
				Set.of("Controller", "Save"), Set.of("usecase", "controller", "save"),
				TemplateGroup.USE_CASE_SAVE);
		register(templates, "SavePersistenceService", "Persistence service for save operations",
				Set.of("PersistenceService", "Save"), Set.of("usecase", "persistence", "save"),
				TemplateGroup.USE_CASE_SAVE);
		register(templates, "SaveServiceFacade", "Facade for save services",
				Set.of("Facade", "Save"), Set.of("usecase", "facade", "save"),
				TemplateGroup.USE_CASE_SAVE);
		register(templates, "UpdateApplicationController", "Application controller for update operations",
				Set.of("Controller", "Update"), Set.of("usecase", "controller", "update"),
				TemplateGroup.USE_CASE_UPDATE);
		register(templates, "UpdatePersistenceService", "Persistence service for update operations",
				Set.of("PersistenceService", "Update"), Set.of("usecase", "persistence", "update"),
				TemplateGroup.USE_CASE_UPDATE);
		register(templates, "UpdateServiceFacade", "Facade for update services",
				Set.of("Facade", "Update"), Set.of("usecase", "facade", "update"),
				TemplateGroup.USE_CASE_UPDATE);
		register(templates, "MutationConfiguration", "Application mutation lane configuration",
				Set.of("MutationService", "MutationApplicationController", "MutationHandlerRegistry"),
				Set.of("usecase", "mutation", "configuration"),
				TemplateGroup.USE_CASE_MUTATION);
		register(templates, "SavePostCommitMutation", "Post-commit mutation stub for save operations",
				Set.of("PostCommitMutation", "Save"), Set.of("usecase", "postcommit", "save"),
				TemplateGroup.USE_CASE_POST_COMMIT);
		register(templates, "UpdatePostCommitMutation", "Post-commit mutation stub for update operations",
				Set.of("PostCommitMutation", "Update"), Set.of("usecase", "postcommit", "update"),
				TemplateGroup.USE_CASE_POST_COMMIT);
		register(templates, "DeletePostCommitMutation", "Post-commit mutation stub for delete operations",
				Set.of("PostCommitMutation", "Delete"), Set.of("usecase", "postcommit", "delete"),
				TemplateGroup.USE_CASE_POST_COMMIT);
		register(templates, "SaveSubprocessTrigger", "Durable subprocess trigger for save operations",
				Set.of("DurableProcessTrigger", "Save"), Set.of("usecase", "subprocess", "save", "trigger"),
				TemplateGroup.USE_CASE_SUBPROCESS);
		register(templates, "SaveSubprocessPayload", "Durable subprocess payload for save operations",
				Set.of("DurableProcessPayload", "Save"), Set.of("usecase", "subprocess", "save", "payload"),
				TemplateGroup.USE_CASE_SUBPROCESS);
		register(templates, "SaveSubprocessExecutor", "Durable subprocess executor for save operations",
				Set.of("DurableProcessExecutor", "Save"), Set.of("usecase", "subprocess", "save", "executor"),
				TemplateGroup.USE_CASE_SUBPROCESS);
		register(templates, "SaveSubprocessConfiguration", "Durable subprocess configuration for save operations",
				Set.of("DurableProcessDefinition", "Save", "Configuration"),
				Set.of("usecase", "subprocess", "save", "configuration"),
				TemplateGroup.USE_CASE_SUBPROCESS);
		register(templates, "UpdateSubprocessTrigger", "Durable subprocess trigger for update operations",
				Set.of("DurableProcessTrigger", "Update"), Set.of("usecase", "subprocess", "update", "trigger"),
				TemplateGroup.USE_CASE_SUBPROCESS);
		register(templates, "UpdateSubprocessPayload", "Durable subprocess payload for update operations",
				Set.of("DurableProcessPayload", "Update"), Set.of("usecase", "subprocess", "update", "payload"),
				TemplateGroup.USE_CASE_SUBPROCESS);
		register(templates, "UpdateSubprocessExecutor", "Durable subprocess executor for update operations",
				Set.of("DurableProcessExecutor", "Update"),
				Set.of("usecase", "subprocess", "update", "executor"),
				TemplateGroup.USE_CASE_SUBPROCESS);
		register(templates, "UpdateSubprocessConfiguration",
				"Durable subprocess configuration for update operations",
				Set.of("DurableProcessDefinition", "Update", "Configuration"),
				Set.of("usecase", "subprocess", "update", "configuration"),
				TemplateGroup.USE_CASE_SUBPROCESS);
		register(templates, "DeleteSubprocessTrigger", "Durable subprocess trigger for delete operations",
				Set.of("DurableProcessTrigger", "Delete"), Set.of("usecase", "subprocess", "delete", "trigger"),
				TemplateGroup.USE_CASE_SUBPROCESS);
		register(templates, "DeleteSubprocessPayload", "Durable subprocess payload for delete operations",
				Set.of("DurableProcessPayload", "Delete"), Set.of("usecase", "subprocess", "delete", "payload"),
				TemplateGroup.USE_CASE_SUBPROCESS);
		register(templates, "DeleteSubprocessExecutor", "Durable subprocess executor for delete operations",
				Set.of("DurableProcessExecutor", "Delete"),
				Set.of("usecase", "subprocess", "delete", "executor"),
				TemplateGroup.USE_CASE_SUBPROCESS);
		register(templates, "DeleteSubprocessConfiguration",
				"Durable subprocess configuration for delete operations",
				Set.of("DurableProcessDefinition", "Delete", "Configuration"),
				Set.of("usecase", "subprocess", "delete", "configuration"),
				TemplateGroup.USE_CASE_SUBPROCESS);
	}

	private static void register(
			final Map<String, TemplateMetadataConfig> templates,
			final String templateName,
			final String description,
			final Set<String> dependencies,
			final Set<String> tags,
			final TemplateGroup templateGroup)
	{
		putUnique(templates, templateName, new TemplateMetadataConfig(
				new TemplateMetadata(
						Optional.of(description),
						dependencies,
						tags,
						Optional.of("Clean CRUD Generator"),
						Optional.of("1.0"),
						false
				),
				templateGroup
		));
	}

	private static void putUnique(
			final Map<String, TemplateMetadataConfig> templates,
			final String templateName,
			final TemplateMetadataConfig metadata)
	{
		if (templates.containsKey(templateName))
		{
			throw new IllegalStateException("Duplicate template metadata registration for `" + templateName + "`");
		}
		templates.put(templateName, metadata);
	}

	public static Optional<TemplateMetadataConfig> getTemplateMetadata(final String templateName)
	{
		return Optional.ofNullable(TEMPLATE_METADATA_MAP.get(templateName));
	}

	public static Map<String, TemplateMetadataConfig> getAllTemplateMetadata()
	{
		return Map.copyOf(TEMPLATE_METADATA_MAP);
	}

	public static boolean hasTemplate(final String templateName)
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
