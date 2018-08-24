# RxThreadEffectChecker

The RxThreadEffectChecker is a static analysis tool for preventing UI access off the main thread in stream-based (RxJava/RxAndroid) Android applications.

We are publishing this code as a companion artifact for our ASE 2018 paper:

> **Safe Stream-Based Programming with Refinement Types**. Benno Stein, Lazaro Clapp, Manu Sridharan, Bor-Yuh Evan Chang. ASE 2018 (arxiv: https://arxiv.org/abs/1808.02998 )

This checker builds heavily on the [Checker Framework](https://checkerframework.org/), and particularly it works in tandem with their `guieffect` checker. Besides the code in this repository, we have worked to upstream changes to the Checker Framework that make this checker possible.  Note that running this checker requires using `javac` from JDK 8, and hence it cannot be run alongside [Error Prone](http://errorprone.info/) in the same build.


# From the ASE 2018 paper's abstract

In stream-based programming, data sources are abstracted as a stream of values that can be manipulated via callback functions. Stream-based programming is exploding in popularity, as it provides a powerful and expressive paradigm for handling asynchronous data sources in interactive software. However, high-level stream abstractions can also make it difficult for developers to reason about control- and data-flow relationships in their programs. This is particularly impactful when asynchronous stream-based code interacts with thread-limited features such as UI frameworks that restrict UI access to a single thread, since the threading behavior of streaming constructs is often non-intuitive and insufficiently documented. 
In this paper, we present a type-based approach that can statically prove the thread-safety of UI accesses in stream-based software. Our key insight is that the fluent APIs of stream-processing frameworks enable the tracking of threads via type-refinement, making it possible to reason automatically about what thread a piece of code runs on -- a difficult problem in general.

We implement the system as an annotation-based Java typechecker for Android programs built upon the popular ReactiveX framework and evaluate its efficacy by annotating and analyzing 8 open-source apps, where we find 33 instances of unsafe UI access while incurring an annotation burden of only one annotation per 186 source lines of code. We also report on our experience applying the typechecker to two much larger apps from the Uber Technologies Inc. codebase, where it currently runs on every code change and blocks changes that introduce potential threading bugs.

# Building and running basic unit tests

`./gradlew build`

# Running ASE 2018 paper experiments

ToDo: Benno(?)

# In progress:

* Switch to CF 2.5.5 as soon as they release, which has our inference and `@MainThread` support fixes.
* Add ASE 2018 experiments
