package java.util.concurrent.atomic

// Warning: The current implementation of this entire package relies on
//          Scala Native being single threaded.

import java.util.function.UnaryOperator

class AtomicReference[T <: AnyRef](private var value: T) extends Serializable {

  def this() = this(null.asInstanceOf[T])

  final def get(): T = value

  final def set(newValue: T): Unit =
    value = newValue

  final def lazySet(newValue: T): Unit =
    set(newValue)

  final def compareAndSet(expect: T, update: T): Boolean = {
    if (expect ne value) false
    else {
      value = update
      true
    }
  }

  final def weakCompareAndSet(expect: T, update: T): Boolean =
    compareAndSet(expect, update)

  final def getAndSet(newValue: T): T = {
    val old = value
    value = newValue
    old
  }

  final def getAndUpdate(updateFunction: UnaryOperator[T]): T = {
    val old = value
    value = updateFunction(old)
    old
  }

  final def updateAndGet(updateFunction: UnaryOperator[T]): T = {
    value = updateFunction(value)
    value
  }

  override def toString(): String =
    String.valueOf(value)
}
