/**
 */
package org.openscada.contrib.da.server.exporter;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Hive Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.openscada.contrib.da.server.exporter.HiveType#getConfiguration <em>Configuration</em>}</li>
 *   <li>{@link org.openscada.contrib.da.server.exporter.HiveType#getExport <em>Export</em>}</li>
 *   <li>{@link org.openscada.contrib.da.server.exporter.HiveType#getFactory <em>Factory</em>}</li>
 *   <li>{@link org.openscada.contrib.da.server.exporter.HiveType#getRef <em>Ref</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.openscada.contrib.da.server.exporter.ExporterPackage#getHiveType()
 * @model extendedMetaData="name='HiveType' kind='elementOnly'"
 * @generated
 */
public interface HiveType extends EObject
{
    /**
     * Returns the value of the '<em><b>Configuration</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Configuration</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Configuration</em>' containment reference.
     * @see #setConfiguration(HiveConfigurationType)
     * @see org.openscada.contrib.da.server.exporter.ExporterPackage#getHiveType_Configuration()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='configuration' namespace='##targetNamespace'"
     * @generated
     */
    HiveConfigurationType getConfiguration();

    /**
     * Sets the value of the '{@link org.openscada.contrib.da.server.exporter.HiveType#getConfiguration <em>Configuration</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Configuration</em>' containment reference.
     * @see #getConfiguration()
     * @generated
     */
    void setConfiguration(HiveConfigurationType value);

    /**
     * Returns the value of the '<em><b>Export</b></em>' containment reference list.
     * The list contents are of type {@link org.openscada.contrib.da.server.exporter.ExportType}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Export</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Export</em>' containment reference list.
     * @see org.openscada.contrib.da.server.exporter.ExporterPackage#getHiveType_Export()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='export' namespace='##targetNamespace'"
     * @generated
     */
    EList<ExportType> getExport();

    /**
     * Returns the value of the '<em><b>Factory</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * 
     *     				A factory class which should be used to create the
     *     				referenced hive. If omitted this is the default
     *     				object instantiation factory.
     *     			
     * <!-- end-model-doc -->
     * @return the value of the '<em>Factory</em>' attribute.
     * @see #setFactory(String)
     * @see org.openscada.contrib.da.server.exporter.ExporterPackage#getHiveType_Factory()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="kind='attribute' name='factory'"
     * @generated
     */
    String getFactory();

    /**
     * Sets the value of the '{@link org.openscada.contrib.da.server.exporter.HiveType#getFactory <em>Factory</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Factory</em>' attribute.
     * @see #getFactory()
     * @generated
     */
    void setFactory(String value);

    /**
     * Returns the value of the '<em><b>Ref</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * 
     *     				The reference to the hive to export. By default the
     *     				class factory is used so this is a class name to
     *     				instantiate and export.
     *     			
     * <!-- end-model-doc -->
     * @return the value of the '<em>Ref</em>' attribute.
     * @see #setRef(String)
     * @see org.openscada.contrib.da.server.exporter.ExporterPackage#getHiveType_Ref()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
     *        extendedMetaData="kind='attribute' name='ref'"
     * @generated
     */
    String getRef();

    /**
     * Sets the value of the '{@link org.openscada.contrib.da.server.exporter.HiveType#getRef <em>Ref</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Ref</em>' attribute.
     * @see #getRef()
     * @generated
     */
    void setRef(String value);

} // HiveType
