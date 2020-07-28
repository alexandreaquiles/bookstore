# bookstore

## Day 1

### Installing Datomic

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

### Links

Rich quoting Heraclitus: https://www.youtube.com/watch?v=E4RarTAZ2AY&feature=youtu.be&t=1192 

More Heraclitus quotes: https://en.wikiquote.org/wiki/Heraclitus#Quotes

Clojure’s approach to Identity and State: https://clojure.org/about/state
> The set of my favorite foods doesn’t change, i.e. if I prefer different foods in the future, that will be a different set.

Datomic Rationale and comparison of architecture with traditional DBs: https://docs.datomic.com/on-prem/rationale.html 
More about Datomic's architecture: https://docs.datomic.com/on-prem/architecture.html

Open source Datomic alternative: https://github.com/juxt/crux 

Datomic Schema: https://docs.datomic.com/on-prem/schema.html 


Datalog language, a subset of Prolog: https://en.wikipedia.org/wiki/Datalog

## Day 2

### Links

Docs on the list and map form of Datomic transactions: https://docs.datomic.com/on-prem/transactions.html

Weird Characters in Clojure (like `#inst`): https://clojure.org/guides/weird_characters#tagged_literals

The five common forms of Clojure keywords (via Kazuki): https://blog.jeaye.com/2017/10/31/clojure-keywords/

Datomic Workshop by it's creators: https://docs.datomic.com/on-prem/day-of-datomic.html

## Day 3

### Links

Datomic Courses (in pt_BR): https://www.alura.com.br/formacao-datomic

Intro to DDD: https://www.amazon.com.br/Domain-Driven-Design-Distilled-Vaughn-Vernon/dp/0134434420
Books on DDD: https://dddcommunity.org/books/

Domain Events: https://microservices.io/patterns/data/domain-event.html

Event Storming (the workshop): https://en.wikipedia.org/wiki/Event_storming
Event Storming (the workshop): https://www.eventstorming.com/

Event Sourcing (the technique): https://microservices.io/patterns/data/event-sourcing.html
Event Sourcing (the technique): https://martinfowler.com/eaaDev/EventSourcing.html

Datomic Console: https://docs.datomic.com/on-prem/console.html

Java UUIDs: https://docs.oracle.com/javase/7/docs/api/java/util/UUID.html
Examples of using UUIDs in Java: https://www.baeldung.com/java-uuid

Identity and Uniqueness in Datomic: https://docs.datomic.com/on-prem/identity.html

Clojure books from The Pragmatic Bookshelf: https://pragprog.com/search/?q=clojure

Why's Poignant Guide to Ruby: https://poignant.guide/
Land of Lisp (a Common Lisp book): http://landoflisp.com/
SICP (code in Scheme): https://mitpress.mit.edu/sites/default/files/sicp/index.html
