/**
 */
package org.openscada.contrib.da.server.exporter;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Export Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.openscada.contrib.da.server.exporter.ExportType#getUri <em>Uri</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.openscada.contrib.da.server.exporter.ExporterPackage#getExportType()
 * @model extendedMetaData="name='ExportType' kind='empty'"
 * @generated
 */
public interface ExportType extends EObject
{
    /**
     * Returns the value of the '<em><b>Uri</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * The connection string URI
     * <!-- end-model-doc -->
     * @return the value of the '<em>Uri</em>' attribute.
     * @see #setUri(String)
     * @see org.openscada.contrib.da.server.exporter.ExporterPackage#getExportType_Uri()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
     *        extendedMetaData="kind='attribute' name='uri'"
     * @generated
     */
    String getUri();

    /**
     * Sets the value of the '{@link org.openscada.contrib.da.server.exporter.ExportType#getUri <em>Uri</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Uri</em>' attribute.
     * @see #getUri()
     * @generated
     */
    void setUri(String value);

} // ExportType
