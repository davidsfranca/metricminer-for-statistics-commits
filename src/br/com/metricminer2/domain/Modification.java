/**
 * Copyright 2014 Maurício Aniche

 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at

 * http://www.apache.org/licenses/LICENSE-2.0

 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package br.com.metricminer2.domain;


public class Modification {

	private String oldPath;
	private String newPath;
	private ModificationType type;
	private String diff;
	private String sourceCode;
	private String workTree;

	public Modification(String workTree, String oldPath, String newPath, ModificationType type, String diff, String sourceCode) {
		this.oldPath = oldPath;
		this.newPath = newPath;
		this.type = type;
		this.diff = diff;
		this.sourceCode = sourceCode;
		this.workTree = workTree;
	}

	public String getOldPath() {
		return oldPath;
	}

	public String getNewPath() {
		return newPath;
	}

	public ModificationType getType() {
		return type;
	}

	public String getDiff() {
		return diff;
	}

	public String getSourceCode() {
		return sourceCode;
	}

	@Override
	public String toString() {
		return "Modification [oldPath=" + oldPath + ", newPath=" + newPath + ", type=" + type
				+ "]";
	}

	public boolean wasDeleted() {
		return type.equals(ModificationType.DELETE);
	}

	public String getWorkTree() {
		return workTree;
	}
	
}
