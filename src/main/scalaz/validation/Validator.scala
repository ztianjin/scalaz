package scalaz.validation

/**
 * Validators using the kleisli structure.
 *
 * @see Kleisli
 * @author <a href="mailto:code@tmorris.net">Tony Morris</a>
 * @version $LastChangedRevision$<br>
 *          $LastChangedDate$<br>
 *          $LastChangedBy$
 */
object Validator {
  import control.Kleisli
  import control.Kleisli._
  import list.NonEmptyList

  type Validator[E, -A, B] = control.Kleisli[PartialType[Validation, E]#Apply, A, B]

  import Validation.{success, fail}

  /**
   * A validator for converting a String to an Int with the potential for a NumberFormatException.
   */
  val parseInt = kleisli[PartialType[Validation, NumberFormatException]#Apply](
    (s: String) => try { success(s.toInt) } catch { case e: NumberFormatException => fail(e) })

  /**
   * A validator for ensuring a list is not empty.
   */
  def notEmpty[A] = kleisli[Option]((cs: List[A]) => (cs: Option[NonEmptyList[A]]))
}