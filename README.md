# MyHealth

> MyHealth application

- [Deploy](#deploy)
- [Getting Started](#getting-started)
- [Conventional Commits](#conventional-commits)

## Deploy

To Deploy and compile the application we provide a scripts ant.

- **Pre requisits**: To have ant, java, jboss and mysql installed, also it will be necessary to have the environment variable JBOSS_HOME. As is explained in the file `Tutorial instalación. Laboratorio PDSv8.0e.pdf`

To clean, build and deploy in the JBOSS_HOME directory execute

```
ant all
```

> As fallback mode we provide a pre compilated ear file ready to be deployed in the server. Can be found in `myhealtee/MyHealth.ear`

## Getting Started

> This step is only necessary for the developers.

Nodejs installation

> Is important to install nodejs locally because will control that you write a correct commit messages and allow you to autogenerate the changelog file.
> See [more](#conventional-commits)

Download nodejs if is not already installed. https://nodejs.org/en/download/

Once node is installed install the node modules (from the root folder directory of myhealth):

```
npm install
```

### Conventional Commits

https://www.conventionalcommits.org/en/v1.0.0/

###### Type

Must be one of the following:

- build: Changes that affect the build system or external dependencies (example scopes: gulp, broccoli, npm)
- ci: Changes to our CI configuration files and scripts (example scopes: Travis, Circle, BrowserStack, SauceLabs)
- docs: Documentation only changes
- feat: A new feature
- fix: A bug fix
- perf: A code change that improves performance
- refactor: A code change that neither fixes a bug nor adds a feature
- style: Changes that do not affect the meaning of the code (white-space, - formatting, missing semi-colons, etc)
  test: Adding missing tests or correcting existing tests

The commit contains the following structural elements, to communicate intent to the consumers of your library:

- fix: a commit of the type fix patches a bug in your codebase (this correlates with PATCH in semantic versioning).
- feat: a commit of the type feat introduces a new feature to the codebase (this correlates with MINOR in semantic versioning).
- BREAKING CHANGE: a commit that has a footer BREAKING CHANGE:, or appends a ! after the type/scope, introduces a breaking API change (correlating with MAJOR in semantic versioning). A BREAKING CHANGE can be part of commits of any type.
- types other than fix: and feat: are allowed, for example @commitlint/config-conventional (based on the the Angular convention) recommends build:, chore:, ci:, docs:, style:, refactor:, perf:, test:, and others.
- footers other than BREAKING CHANGE: <description> may be provided and follow a convention similar to git trailer format.

Additional types are not mandated by the conventional commits specification, and have no implicit effect in semantic versioning (unless they include a BREAKING CHANGE).
