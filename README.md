# bookstore

# Day 1

## Installing Datomic

On-premise installation:
https://www.datomic.com/on-prem.html 

To create an account:

https://my.datomic.com/account/create 

Version downloaded: `0.9.6045`

To configure an embedded db:

```
cp config/samples/dev-transactor-template.properties config/dev-transactor.properties 
```

And then change `config/dev-transactor.propertie` to include the license key you got by email.

To run the transactor:

```
bin/transactor config/dev-transactor.properties 
```

Installing Maven with [SDKMan](https://sdkman.io/)

```
curl -s "https://get.sdkman.io" | bash 
sdk install maven 3.6.3 
```

Installing Datomic Pro Peer Library into our local Maven repo (`~/.m2`)

```sh
bin/maven-install
```

Alternatives for installing the Peer Library: https://docs.datomic.com/on-prem/integrating-peer-lib.html

## Links

Rich quoting Heraclitus: https://www.youtube.com/watch?v=E4RarTAZ2AY&feature=youtu.be&t=1192 

More Heraclitus quotes: https://en.wikiquote.org/wiki/Heraclitus#Quotes

Clojure’s approach to Identity and State: https://clojure.org/about/state
> The set of my favorite foods doesn’t change, i.e. if I prefer different foods in the future, that will be a different set.

Datomic Rationale and comparison of architecture with traditional DBs: https://docs.datomic.com/on-prem/rationale.html 
More about Datomic's architecture: https://docs.datomic.com/on-prem/architecture.html

Open source Datomic alternative: https://github.com/juxt/crux 

Datomic Schema: https://docs.datomic.com/on-prem/schema.html 


Datalog languege, a subset of Prolog: https://en.wikipedia.org/wiki/Datalog
