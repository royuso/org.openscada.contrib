/*
 * This file is part of the openSCADA import hudson.model.Result;

import org.openscada.utils.beans.AbstractPropertyChange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
it under the terms of the GNU Lesser General Public License version 3
 * only, as published by the Free Software Foundation.
 *
 * openSCADA is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License version 3 for more details
 * (a copy is included in the LICENSE file that accompanied this code).
 *
 * You should have received a copy of the GNU Lesser General Public License
 * version 3 along with openSCADA. If not, see
 * <http://opensource.org/licenses/lgpl-3.0.html> for a copy of the LGPLv3 License.
 */

package org.openscada.jenkins.exporter;

import hudson.model.Run;

public class ProjectStateUpdater
{
    private final ProjectState projectState;

    public ProjectStateUpdater ( final ProjectState projectState )
    {
        this.projectState = projectState;
    }

    public void update ( final Run<?, ?> run )
    {
        if ( run != null )
        {
            this.projectState.setRunning ( run.isBuilding () );
            this.projectState.setResult ( run.getResult () );
        }
    }
}
