/**
 */
package org.openscada.contrib.da.server.exporter;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Configuration Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.openscada.contrib.da.server.exporter.ConfigurationType#getHive <em>Hive</em>}</li>
 *   <li>{@link org.openscada.contrib.da.server.exporter.ConfigurationType#getAnnouncer <em>Announcer</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.openscada.contrib.da.server.exporter.ExporterPackage#getConfigurationType()
 * @model extendedMetaData="name='ConfigurationType' kind='elementOnly'"
 * @generated
 */
public interface ConfigurationType extends EObject
{
    /**
     * Returns the value of the '<em><b>Hive</b></em>' containment reference list.
     * The list contents are of type {@link org.openscada.contrib.da.server.exporter.HiveType}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Hive</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Hive</em>' containment reference list.
     * @see org.openscada.contrib.da.server.exporter.ExporterPackage#getConfigurationType_Hive()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='hive' namespace='##targetNamespace'"
     * @generated
     */
    EList<HiveType> getHive();

    /**
     * Returns the value of the '<em><b>Announcer</b></em>' containment reference list.
     * The list contents are of type {@link org.openscada.contrib.da.server.exporter.AnnouncerType}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Announcer</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Announcer</em>' containment reference list.
     * @see org.openscada.contrib.da.server.exporter.ExporterPackage#getConfigurationType_Announcer()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='announcer' namespace='##targetNamespace'"
     * @generated
     */
    EList<AnnouncerType> getAnnouncer();

} // ConfigurationType
