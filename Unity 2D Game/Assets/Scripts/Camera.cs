using UnityEngine;

public class CameraMovement : MonoBehaviour
{
    public Transform player; // Igra� kojeg kamera prati
    public Vector3 offset; // Pomeraj kamere (z za dubinu)
    public float minX; // Minimalna granica kamere po x osi
    public float maxX; // Maksimalna granica kamere po x osi
    public float halfScreenWidth = 5f; // Polovina �irine ekrana u jedinicama igre

    void Update()
    {
        // Ra�unaj poziciju igra�a u odnosu na trenutnu poziciju kamere
        float playerXRelativeToCamera = player.position.x - transform.position.x;

        // Pomeri kameru ako igra� pre�e prag
        if (playerXRelativeToCamera > halfScreenWidth)
        {
            // Igra� prelazi desnu polovinu - pomeraj kameru desno
            float targetX = transform.position.x + (playerXRelativeToCamera - halfScreenWidth);
            transform.position = new Vector3(
                Mathf.Clamp(targetX, minX, maxX),
                transform.position.y,
                offset.z
            );
        }
        else if (playerXRelativeToCamera < -halfScreenWidth)
        {
            // Igra� prelazi levu polovinu - pomeraj kameru levo
            float targetX = transform.position.x + (playerXRelativeToCamera + halfScreenWidth);
            transform.position = new Vector3(
                Mathf.Clamp(targetX, minX, maxX),
                transform.position.y,
                offset.z
            );
        }
    }
}


