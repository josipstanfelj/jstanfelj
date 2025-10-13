using UnityEngine;
using UnityEngine.Rendering.Universal; // Potrebno za 2D light komponente

public class PlayerLightController : MonoBehaviour
{
    public GameObject player; // Referenca na igraèa
    public Light2D pointLight; // Referenca na Point Light 2D
    public float innerRadius = 3f; // Unutrašnji radijus svetla
    public float outerRadius = 5f; // Spoljašnji radijus svetla

    void Start()
    {
        if (pointLight == null)
        {
            Debug.LogError("Point Light 2D nije postavljen!");
        }
        pointLight.pointLightInnerRadius = innerRadius;
        pointLight.pointLightOuterRadius = outerRadius;
    }

    void Update()
    {
        if (player != null && pointLight != null)
        {
            // Postavi svetlo na poziciju igraèa
            pointLight.transform.position = player.transform.position;
        }
    }
}

