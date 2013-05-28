/**
 */
package org.openscada.contrib.da.server.exporter.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.eclipse.emf.ecore.xml.type.XMLTypePackage;

import org.openscada.contrib.da.server.exporter.AnnouncerType;
import org.openscada.contrib.da.server.exporter.ConfigurationType;
import org.openscada.contrib.da.server.exporter.DocumentRoot;
import org.openscada.contrib.da.server.exporter.ExportType;
import org.openscada.contrib.da.server.exporter.ExporterFactory;
import org.openscada.contrib.da.server.exporter.ExporterPackage;
import org.openscada.contrib.da.server.exporter.HiveConfigurationType;
import org.openscada.contrib.da.server.exporter.HiveType;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class ExporterPackageImpl extends EPackageImpl implements ExporterPackage
{
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass announcerTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass configurationTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass documentRootEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass exportTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass hiveConfigurationTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass hiveTypeEClass = null;

    /**
     * Creates an instance of the model <b>Package</b>, registered with
     * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
     * package URI value.
     * <p>Note: the correct way to create the package is via the static
     * factory method {@link #init init()}, which also performs
     * initialization of the package, or returns the registered package,
     * if one already exists.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.emf.ecore.EPackage.Registry
     * @see org.openscada.contrib.da.server.exporter.ExporterPackage#eNS_URI
     * @see #init()
     * @generated
     */
    private ExporterPackageImpl()
    {
        super(eNS_URI, ExporterFactory.eINSTANCE);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private static boolean isInited = false;

    /**
     * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
     * 
     * <p>This method is used to initialize {@link ExporterPackage#eINSTANCE} when that field is accessed.
     * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #eNS_URI
     * @see #createPackageContents()
     * @see #initializePackageContents()
     * @generated
     */
    public static ExporterPackage init()
    {
        if (isInited) return (ExporterPackage)EPackage.Registry.INSTANCE.getEPackage(ExporterPackage.eNS_URI);

        // Obtain or create and register package
        ExporterPackageImpl theExporterPackage = (ExporterPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof ExporterPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new ExporterPackageImpl());

        isInited = true;

        // Initialize simple dependencies
        XMLTypePackage.eINSTANCE.eClass();

        // Create package meta-data objects
        theExporterPackage.createPackageContents();

        // Initialize created meta-data
        theExporterPackage.initializePackageContents();

        // Mark meta-data to indicate it can't be changed
        theExporterPackage.freeze();

  
        // Update the registry and return the package
        EPackage.Registry.INSTANCE.put(ExporterPackage.eNS_URI, theExporterPackage);
        return theExporterPackage;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getAnnouncerType()
    {
        return announcerTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getAnnouncerType_Class()
    {
        return (EAttribute)announcerTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getConfigurationType()
    {
        return configurationTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getConfigurationType_Hive()
    {
        return (EReference)configurationTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getConfigurationType_Announcer()
    {
        return (EReference)configurationTypeEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getDocumentRoot()
    {
        return documentRootEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getDocumentRoot_Mixed()
    {
        return (EAttribute)documentRootEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_XMLNSPrefixMap()
    {
        return (EReference)documentRootEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_XSISchemaLocation()
    {
        return (EReference)documentRootEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_Configuration()
    {
        return (EReference)documentRootEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getExportType()
    {
        return exportTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getExportType_Uri()
    {
        return (EAttribute)exportTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getHiveConfigurationType()
    {
        return hiveConfigurationTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getHiveConfigurationType_Any()
    {
        return (EAttribute)hiveConfigurationTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getHiveType()
    {
        return hiveTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getHiveType_Configuration()
    {
        return (EReference)hiveTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getHiveType_Export()
    {
        return (EReference)hiveTypeEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getHiveType_Factory()
    {
        return (EAttribute)hiveTypeEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getHiveType_Ref()
    {
        return (EAttribute)hiveTypeEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ExporterFactory getExporterFactory()
    {
        return (ExporterFactory)getEFactoryInstance();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private boolean isCreated = false;

    /**
     * Creates the meta-model objects for the package.  This method is
     * guarded to have no affect on any invocation but its first.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void createPackageContents()
    {
        if (isCreated) return;
        isCreated = true;

        // Create classes and their features
        announcerTypeEClass = createEClass(ANNOUNCER_TYPE);
        createEAttribute(announcerTypeEClass, ANNOUNCER_TYPE__CLASS);

        configurationTypeEClass = createEClass(CONFIGURATION_TYPE);
        createEReference(configurationTypeEClass, CONFIGURATION_TYPE__HIVE);
        createEReference(configurationTypeEClass, CONFIGURATION_TYPE__ANNOUNCER);

        documentRootEClass = createEClass(DOCUMENT_ROOT);
        createEAttribute(documentRootEClass, DOCUMENT_ROOT__MIXED);
        createEReference(documentRootEClass, DOCUMENT_ROOT__XMLNS_PREFIX_MAP);
        createEReference(documentRootEClass, DOCUMENT_ROOT__XSI_SCHEMA_LOCATION);
        createEReference(documentRootEClass, DOCUMENT_ROOT__CONFIGURATION);

        exportTypeEClass = createEClass(EXPORT_TYPE);
        createEAttribute(exportTypeEClass, EXPORT_TYPE__URI);

        hiveConfigurationTypeEClass = createEClass(HIVE_CONFIGURATION_TYPE);
        createEAttribute(hiveConfigurationTypeEClass, HIVE_CONFIGURATION_TYPE__ANY);

        hiveTypeEClass = createEClass(HIVE_TYPE);
        createEReference(hiveTypeEClass, HIVE_TYPE__CONFIGURATION);
        createEReference(hiveTypeEClass, HIVE_TYPE__EXPORT);
        createEAttribute(hiveTypeEClass, HIVE_TYPE__FACTORY);
        createEAttribute(hiveTypeEClass, HIVE_TYPE__REF);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private boolean isInitialized = false;

    /**
     * Complete the initialization of the package and its meta-model.  This
     * method is guarded to have no affect on any invocation but its first.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void initializePackageContents()
    {
        if (isInitialized) return;
        isInitialized = true;

        // Initialize package
        setName(eNAME);
        setNsPrefix(eNS_PREFIX);
        setNsURI(eNS_URI);

        // Obtain other dependent packages
        XMLTypePackage theXMLTypePackage = (XMLTypePackage)EPackage.Registry.INSTANCE.getEPackage(XMLTypePackage.eNS_URI);

        // Create type parameters

        // Set bounds for type parameters

        // Add supertypes to classes

        // Initialize classes and features; add operations and parameters
        initEClass(announcerTypeEClass, AnnouncerType.class, "AnnouncerType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getAnnouncerType_Class(), theXMLTypePackage.getString(), "class", null, 0, 1, AnnouncerType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(configurationTypeEClass, ConfigurationType.class, "ConfigurationType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getConfigurationType_Hive(), this.getHiveType(), null, "hive", null, 0, -1, ConfigurationType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getConfigurationType_Announcer(), this.getAnnouncerType(), null, "announcer", null, 0, -1, ConfigurationType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(documentRootEClass, DocumentRoot.class, "DocumentRoot", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getDocumentRoot_Mixed(), ecorePackage.getEFeatureMapEntry(), "mixed", null, 0, -1, null, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getDocumentRoot_XMLNSPrefixMap(), ecorePackage.getEStringToStringMapEntry(), null, "xMLNSPrefixMap", null, 0, -1, null, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getDocumentRoot_XSISchemaLocation(), ecorePackage.getEStringToStringMapEntry(), null, "xSISchemaLocation", null, 0, -1, null, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getDocumentRoot_Configuration(), this.getConfigurationType(), null, "configuration", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);

        initEClass(exportTypeEClass, ExportType.class, "ExportType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getExportType_Uri(), theXMLTypePackage.getString(), "uri", null, 1, 1, ExportType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(hiveConfigurationTypeEClass, HiveConfigurationType.class, "HiveConfigurationType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getHiveConfigurationType_Any(), ecorePackage.getEFeatureMapEntry(), "any", null, 0, 1, HiveConfigurationType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(hiveTypeEClass, HiveType.class, "HiveType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getHiveType_Configuration(), this.getHiveConfigurationType(), null, "configuration", null, 0, 1, HiveType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getHiveType_Export(), this.getExportType(), null, "export", null, 0, -1, HiveType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getHiveType_Factory(), theXMLTypePackage.getString(), "factory", null, 0, 1, HiveType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getHiveType_Ref(), theXMLTypePackage.getString(), "ref", null, 1, 1, HiveType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        // Create resource
        createResource(eNS_URI);

        // Create annotations
        // http:///org/eclipse/emf/ecore/util/ExtendedMetaData
        createExtendedMetaDataAnnotations();
    }

    /**
     * Initializes the annotations for <b>http:///org/eclipse/emf/ecore/util/ExtendedMetaData</b>.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected void createExtendedMetaDataAnnotations()
    {
        String source = "http:///org/eclipse/emf/ecore/util/ExtendedMetaData";		
        addAnnotation
          (announcerTypeEClass, 
           source, 
           new String[] 
           {
             "name", "AnnouncerType",
             "kind", "empty"
           });		
        addAnnotation
          (getAnnouncerType_Class(), 
           source, 
           new String[] 
           {
             "kind", "attribute",
             "name", "class"
           });		
        addAnnotation
          (configurationTypeEClass, 
           source, 
           new String[] 
           {
             "name", "ConfigurationType",
             "kind", "elementOnly"
           });		
        addAnnotation
          (getConfigurationType_Hive(), 
           source, 
           new String[] 
           {
             "kind", "element",
             "name", "hive",
             "namespace", "##targetNamespace"
           });		
        addAnnotation
          (getConfigurationType_Announcer(), 
           source, 
           new String[] 
           {
             "kind", "element",
             "name", "announcer",
             "namespace", "##targetNamespace"
           });		
        addAnnotation
          (documentRootEClass, 
           source, 
           new String[] 
           {
             "name", "",
             "kind", "mixed"
           });		
        addAnnotation
          (getDocumentRoot_Mixed(), 
           source, 
           new String[] 
           {
             "kind", "elementWildcard",
             "name", ":mixed"
           });		
        addAnnotation
          (getDocumentRoot_XMLNSPrefixMap(), 
           source, 
           new String[] 
           {
             "kind", "attribute",
             "name", "xmlns:prefix"
           });		
        addAnnotation
          (getDocumentRoot_XSISchemaLocation(), 
           source, 
           new String[] 
           {
             "kind", "attribute",
             "name", "xsi:schemaLocation"
           });		
        addAnnotation
          (getDocumentRoot_Configuration(), 
           source, 
           new String[] 
           {
             "kind", "element",
             "name", "configuration",
             "namespace", "##targetNamespace"
           });		
        addAnnotation
          (exportTypeEClass, 
           source, 
           new String[] 
           {
             "name", "ExportType",
             "kind", "empty"
           });			
        addAnnotation
          (getExportType_Uri(), 
           source, 
           new String[] 
           {
             "kind", "attribute",
             "name", "uri"
           });		
        addAnnotation
          (hiveConfigurationTypeEClass, 
           source, 
           new String[] 
           {
             "name", "HiveConfigurationType",
             "kind", "elementOnly"
           });		
        addAnnotation
          (getHiveConfigurationType_Any(), 
           source, 
           new String[] 
           {
             "kind", "elementWildcard",
             "wildcards", "##any",
             "name", ":0",
             "processing", "lax"
           });		
        addAnnotation
          (hiveTypeEClass, 
           source, 
           new String[] 
           {
             "name", "HiveType",
             "kind", "elementOnly"
           });		
        addAnnotation
          (getHiveType_Configuration(), 
           source, 
           new String[] 
           {
             "kind", "element",
             "name", "configuration",
             "namespace", "##targetNamespace"
           });		
        addAnnotation
          (getHiveType_Export(), 
           source, 
           new String[] 
           {
             "kind", "element",
             "name", "export",
             "namespace", "##targetNamespace"
           });			
        addAnnotation
          (getHiveType_Factory(), 
           source, 
           new String[] 
           {
             "kind", "attribute",
             "name", "factory"
           });			
        addAnnotation
          (getHiveType_Ref(), 
           source, 
           new String[] 
           {
             "kind", "attribute",
             "name", "ref"
           });
    }

} //ExporterPackageImpl
