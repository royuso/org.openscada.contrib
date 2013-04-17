/**
 */
package org.openscada.da.server.epanet.simulator.configuration;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.openscada.da.server.epanet.simulator.configuration.ConfigurationType#getFile <em>File</em>}</li>
 *   <li>{@link org.openscada.da.server.epanet.simulator.configuration.ConfigurationType#getType <em>Type</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.openscada.da.server.epanet.simulator.configuration.ConfigurationPackage#getConfigurationType()
 * @model extendedMetaData="name='ConfigurationType' kind='empty'"
 * @generated
 */
public interface ConfigurationType extends EObject
{
    /**
     * Returns the value of the '<em><b>File</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>File</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>File</em>' attribute.
     * @see #setFile(String)
     * @see org.openscada.da.server.epanet.simulator.configuration.ConfigurationPackage#getConfigurationType_File()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
     *        extendedMetaData="kind='attribute' name='file'"
     * @generated
     */
    String getFile();

    /**
     * Sets the value of the '{@link org.openscada.da.server.epanet.simulator.configuration.ConfigurationType#getFile <em>File</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>File</em>' attribute.
     * @see #getFile()
     * @generated
     */
    void setFile(String value);

    /**
     * Returns the value of the '<em><b>Type</b></em>' attribute.
     * The default value is <code>"INP"</code>.
     * The literals are from the enumeration {@link org.openscada.da.server.epanet.simulator.configuration.TypeType}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Type</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Type</em>' attribute.
     * @see org.openscada.da.server.epanet.simulator.configuration.TypeType
     * @see #isSetType()
     * @see #unsetType()
     * @see #setType(TypeType)
     * @see org.openscada.da.server.epanet.simulator.configuration.ConfigurationPackage#getConfigurationType_Type()
     * @model default="INP" unsettable="true"
     *        extendedMetaData="kind='attribute' name='type'"
     * @generated
     */
    TypeType getType();

    /**
     * Sets the value of the '{@link org.openscada.da.server.epanet.simulator.configuration.ConfigurationType#getType <em>Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Type</em>' attribute.
     * @see org.openscada.da.server.epanet.simulator.configuration.TypeType
     * @see #isSetType()
     * @see #unsetType()
     * @see #getType()
     * @generated
     */
    void setType(TypeType value);

    /**
     * Unsets the value of the '{@link org.openscada.da.server.epanet.simulator.configuration.ConfigurationType#getType <em>Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetType()
     * @see #getType()
     * @see #setType(TypeType)
     * @generated
     */
    void unsetType();

    /**
     * Returns whether the value of the '{@link org.openscada.da.server.epanet.simulator.configuration.ConfigurationType#getType <em>Type</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Type</em>' attribute is set.
     * @see #unsetType()
     * @see #getType()
     * @see #setType(TypeType)
     * @generated
     */
    boolean isSetType();

} // ConfigurationType
