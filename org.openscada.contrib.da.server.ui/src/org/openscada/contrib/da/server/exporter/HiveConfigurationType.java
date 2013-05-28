/**
 */
package org.openscada.contrib.da.server.exporter;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.emf.ecore.util.FeatureMap;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Hive Configuration Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.openscada.contrib.da.server.exporter.HiveConfigurationType#getAny <em>Any</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.openscada.contrib.da.server.exporter.ExporterPackage#getHiveConfigurationType()
 * @model extendedMetaData="name='HiveConfigurationType' kind='elementOnly'"
 * @generated
 */
public interface HiveConfigurationType extends EObject
{
    /**
     * Returns the value of the '<em><b>Any</b></em>' attribute list.
     * The list contents are of type {@link org.eclipse.emf.ecore.util.FeatureMap.Entry}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Any</em>' attribute list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Any</em>' attribute list.
     * @see org.openscada.contrib.da.server.exporter.ExporterPackage#getHiveConfigurationType_Any()
     * @model dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="false"
     *        extendedMetaData="kind='elementWildcard' wildcards='##any' name=':0' processing='lax'"
     * @generated
     */
    FeatureMap getAny();

} // HiveConfigurationType
