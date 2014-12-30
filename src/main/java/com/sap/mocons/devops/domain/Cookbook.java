package com.sap.mocons.devops.domain;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Cookbook {
	@JsonProperty("name")
	private String name;

	@JsonProperty("updated_at")
	private String updated_at;

	@JsonProperty("created_at")
	private String created_at;

	@JsonProperty("maintainer")
	private String maintainer;

	@JsonProperty("description")
	private String description;

	@JsonProperty("category")
	private String category;

	@JsonProperty("latest_version")
	private Object latest_version;

	@JsonProperty("versions")
	private List<String> versions;

	@JsonProperty("average_raiting")
	private String average_raiting;

	@JsonProperty("external_url")
	private String external_url;

	@JsonProperty("git_url")
	private String git_url;

	private String gitRepositoryUrl;

	public Cookbook() {

	}

	public String getUpdated_at() {
		return updated_at;
	}

	public void setUpdated_at(String updated_at) {
		this.updated_at = updated_at;
	}

	public String getCreated_at() {
		return created_at;
	}

	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}

	public String getMaintainer() {
		return maintainer;
	}

	public void setMaintainer(String maintainer) {
		this.maintainer = maintainer;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getAverage_raiting() {
		return average_raiting;
	}

	public void setAverage_raiting(String average_raiting) {
		this.average_raiting = average_raiting;
	}

	public String getExternal_url() {
		return external_url;
	}

	public void setExternal_url(String external_url) {
		this.external_url = external_url;
		if (external_url != null && !external_url.isEmpty()) {
			setGitRepositoryUrl(external_url.replaceFirst("http:|https:", "git:").concat(".git"));
		}
	}

	public String getGit_url() {
		return git_url;
	}

	public void setGit_url(String git_url) {
		this.git_url = git_url;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getVersions() {
		return versions;
	}

	public void setVersions(List<String> versions) {
		this.versions = versions;
	}

	public Object getLatest_version() {
		return latest_version;
	}

	public void setLatest_version(Object latest_version) {
		this.latest_version = latest_version;
	}

	public String getGitRepositoryUrl() {
		return gitRepositoryUrl;
	}

	public void setGitRepositoryUrl(String gitRepositoryUrl) {
		this.gitRepositoryUrl = gitRepositoryUrl;
	}

	@Override
	public String toString() {
		return "Cookbook [name=" + name + ", updated_at=" + updated_at + ", created_at=" + created_at + ", maintainer="
				+ maintainer + ", description=" + description + ", category=" + category + ", latest_version="
				+ latest_version + ", versions=" + versions + ", average_raiting=" + average_raiting
				+ ", external_url=" + external_url + ", git_url=" + git_url + ", gitRepositoryUrl=" + gitRepositoryUrl
				+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((average_raiting == null) ? 0 : average_raiting.hashCode());
		result = prime * result + ((category == null) ? 0 : category.hashCode());
		result = prime * result + ((created_at == null) ? 0 : created_at.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((external_url == null) ? 0 : external_url.hashCode());
		result = prime * result + ((gitRepositoryUrl == null) ? 0 : gitRepositoryUrl.hashCode());
		result = prime * result + ((git_url == null) ? 0 : git_url.hashCode());
		result = prime * result + ((latest_version == null) ? 0 : latest_version.hashCode());
		result = prime * result + ((maintainer == null) ? 0 : maintainer.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((updated_at == null) ? 0 : updated_at.hashCode());
		result = prime * result + ((versions == null) ? 0 : versions.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cookbook other = (Cookbook) obj;
		if (average_raiting == null) {
			if (other.average_raiting != null)
				return false;
		} else if (!average_raiting.equals(other.average_raiting))
			return false;
		if (category == null) {
			if (other.category != null)
				return false;
		} else if (!category.equals(other.category))
			return false;
		if (created_at == null) {
			if (other.created_at != null)
				return false;
		} else if (!created_at.equals(other.created_at))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (external_url == null) {
			if (other.external_url != null)
				return false;
		} else if (!external_url.equals(other.external_url))
			return false;
		if (gitRepositoryUrl == null) {
			if (other.gitRepositoryUrl != null)
				return false;
		} else if (!gitRepositoryUrl.equals(other.gitRepositoryUrl))
			return false;
		if (git_url == null) {
			if (other.git_url != null)
				return false;
		} else if (!git_url.equals(other.git_url))
			return false;
		if (latest_version == null) {
			if (other.latest_version != null)
				return false;
		} else if (!latest_version.equals(other.latest_version))
			return false;
		if (maintainer == null) {
			if (other.maintainer != null)
				return false;
		} else if (!maintainer.equals(other.maintainer))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (updated_at == null) {
			if (other.updated_at != null)
				return false;
		} else if (!updated_at.equals(other.updated_at))
			return false;
		if (versions == null) {
			if (other.versions != null)
				return false;
		} else if (!versions.equals(other.versions))
			return false;
		return true;
	}

}
