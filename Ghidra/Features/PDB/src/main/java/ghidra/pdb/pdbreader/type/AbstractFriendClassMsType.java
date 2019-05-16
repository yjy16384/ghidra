/* ###
 * IP: GHIDRA
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ghidra.pdb.pdbreader.type;

import ghidra.pdb.PdbByteReader;
import ghidra.pdb.PdbException;
import ghidra.pdb.pdbreader.*;

/**
 * This class represents various flavors of C++ Friend Class type.
 * <P>
 * Note: we do not necessarily understand each of these data type classes.  Refer to the
 *  base class for more information.
 */
public abstract class AbstractFriendClassMsType extends AbstractMsType {

	protected AbstractTypeIndex friendClassTypeIndex;

	/**
	 * Constructor for this type.
	 * @param pdb {@link AbstractPdb} to which this type belongs.
	 * @param reader {@link PdbByteReader} from which this type is deserialized.
	 * @throws PdbException Upon not enough data left to parse.
	 */
	public AbstractFriendClassMsType(AbstractPdb pdb, PdbByteReader reader) throws PdbException {
		super(pdb, reader);
		friendClassTypeIndex = create();
		parseInitialFields(reader);
		friendClassTypeIndex.parse(reader);
		pdb.pushDependencyStack(
			new CategoryIndex(CategoryIndex.Category.DATA, friendClassTypeIndex.get()));
		pdb.popDependencyStack();
	}

	@Override
	public void emit(StringBuilder builder, Bind bind) {
		// TODO: API not documented.  Fix this as figured out.
		builder.append("friend: ");
		builder.append(pdb.getTypeRecord(friendClassTypeIndex.get()).toString());
	}

	/**
	 * Creates subcomponents for this class, which can be deserialized later.
	 * @return the {@link AbstractTypeIndex} type necessary for the {@link #friendClassTypeIndex}
	 * in the concrete class.
	 */
	protected abstract AbstractTypeIndex create();

	/**
	 * Parses the initial fields of this type.
	 * @param reader {@link PdbByteReader} from which the fields are parsed.
	 * @throws PdbException Upon not enough data left to parse.
	 */
	protected abstract void parseInitialFields(PdbByteReader reader) throws PdbException;

}
